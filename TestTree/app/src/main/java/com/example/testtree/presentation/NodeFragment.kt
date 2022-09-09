package com.example.testtree.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.testtree.*
import com.example.testtree.databinding.FragmentNodeBinding
import com.example.testtree.di.DaggerAppComponent
import com.example.testtree.domain.models.ListNodes
import com.example.testtree.domain.models.Node
import com.example.testtree.domain.models.Tree
import java.util.*
import javax.inject.Inject

class NodeFragment : Fragment() {

    lateinit var binding: FragmentNodeBinding

    @Inject
    lateinit var viewModel: NodeViewModel

    lateinit var curNode: Node

    lateinit var rootNode: Node

    lateinit var navController: NavController

    var bundle = Bundle()

    lateinit var listWithNodesNames: LinkedList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val comp = DaggerAppComponent.builder()
            .context(requireContext())
            .build()
        comp.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNodeBinding.inflate(layoutInflater, container, false)
        binding.apply {
            leftBtn.setOnClickListener {
                if (curNode.left != null) {
                    bundle.putParcelable("key", curNode.left)
                    navController.navigate(R.id.action_nodeFragment_self, bundle)
                } else {
                    Toast.makeText(requireContext(), "Have not left child", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            rightBtn.setOnClickListener {
                if (curNode.right != null) {
                    bundle.putParcelable("key", curNode.right)
                    navController.navigate(R.id.action_nodeFragment_self, bundle)
                } else {
                    Toast.makeText(requireContext(), "Have not right child", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            createLeftBtn.setOnClickListener {
                if (curNode.left == null) {
                    curNode.left = Node(parent = curNode, isLeft = true)
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Already have child", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            createRightBtn.setOnClickListener {
                if (curNode.right == null) {
                    curNode.right = Node(parent = curNode, isRight = true)
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Already have child", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            deleteBtn.setOnClickListener {
                if (curNode.isRoot) {
                    Toast.makeText(
                        requireContext(),
                        "You cant delete root node",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.d("TAG", "clicked on delete")
                    if (curNode.isLeft != null) {
                        bundle.putParcelable("deleteleft", curNode.parent)
                        navController.navigate(R.id.action_nodeFragment_self, bundle)
                    } else if (curNode.isRight != null) {
                        bundle.putParcelable("deleteright", curNode.parent)
                        navController.navigate(R.id.action_nodeFragment_self, bundle)
                    }
                }
            }
            parentBtn.setOnClickListener {
                if (curNode.isRoot) {
                    Toast.makeText(
                        requireContext(),
                        "Root node have not parent",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    bundle.putParcelable("key", curNode.parent)
                    navController.navigate(R.id.action_nodeFragment_self, bundle)
                }

            }
        }
        navController = findNavController()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundleNode = arguments?.getParcelable<Node>("key")
        val bundleNodeDeleteleft = arguments?.getParcelable<Node>("deleteleft")
        val bundleNodeDeleteright = arguments?.getParcelable<Node>("deleteright")


        if (bundleNodeDeleteleft == null && bundleNodeDeleteright == null) {
            if (bundleNode != null) {
                curNode = bundleNode
                binding.nameNode.text = curNode.name
                if (curNode.isRoot) binding.rootTv.visibility = View.VISIBLE
                viewModel.getTrees().observe(viewLifecycleOwner, Observer { nodes ->
                    if (!nodes.contains(curNode)) {
                        viewModel.insertTree(curNode)
                        updateListNodesNames()
                        viewModel.getListNodes().observe(viewLifecycleOwner, Observer { listNodes ->
                            if (listNodes.isNotEmpty()) {
                                listWithNodesNames = listNodes.last().listNodesNames
                                nodes.forEach {
                                    if (listWithNodesNames.contains(it.name)) {
                                        if (it.parent?.name == curNode.name) {
                                            if (it.isLeft == true) {
                                                it.parent = curNode
                                                curNode.left = it
                                            } else if (it.isRight == true) {
                                                it.parent = curNode
                                                curNode.right = it
                                            }
                                        }
                                    }
                                }
                            }
                        })
                    }
                })
            } else {
                viewModel.getTrees().observe(viewLifecycleOwner, Observer { nodes ->
                    binding.rootTv.visibility = View.VISIBLE
                    curNode = if (nodes.isEmpty()) {
                        Node(isRoot = true)
                    } else {
                        nodes.first()
                    }
                    binding.nameNode.text = curNode.name
                    if (!nodes.contains(curNode)) {
                        viewModel.insertTree(curNode)
                        updateListNodesNames()
                    }
                    viewModel.getListNodes().observe(viewLifecycleOwner, Observer { listNodes ->
                        if (listNodes.isNotEmpty()) {
                            listWithNodesNames = listNodes.last().listNodesNames
                            nodes.forEach {
                                if (listWithNodesNames.contains(it.name)) {
                                    if (it.parent?.name == curNode.name) {
                                        if (it.isLeft == true) {
                                            it.parent = curNode
                                            curNode.left = it
                                        } else if (it.isRight == true) {
                                            it.parent = curNode
                                            curNode.right = it
                                        }
                                    }
                                }
                            }
                        }
                    })

                })
            }
        } else {
            if (bundleNodeDeleteleft != null) {
                curNode = bundleNodeDeleteleft
                removeListNodesName(curNode.left?.name ?: "")
                val deletedNode = curNode.left
                viewModel.deleteNode(deletedNode!!)
                curNode.left = null
                binding.nameNode.text = curNode.name
                if (curNode.isRoot) binding.rootTv.visibility = View.VISIBLE

            } else if (bundleNodeDeleteright != null) {
                curNode = bundleNodeDeleteright
                removeListNodesName(curNode.right?.name ?: "")
                val deletedNode = curNode.right
                viewModel.deleteNode(deletedNode!!)
                curNode.right = null
                binding.nameNode.text = curNode.name
                if (curNode.isRoot) binding.rootTv.visibility = View.VISIBLE
            }
        }
    }


    private fun updateListNodesNames() {
        viewModel.getListNodes().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                val old = it.last()
                val list = old.listNodesNames
                if (!list.contains(curNode.name)) {
                    list.add(curNode.name)
                    viewModel.deleteList(old)
                    viewModel.insertList(ListNodes(listNodesNames = list))
                }
            }
            else{
                val list = LinkedList<String>()
                list.add(curNode.name)
                viewModel.insertList(ListNodes(listNodesNames = list))
            }
        })
    }

    private fun removeListNodesName(name: String) {
        viewModel.getListNodes().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                val old = it.last()
                val list = old.listNodesNames
                if (list.contains(name)) {
                    list.remove(name)
                    viewModel.deleteList(old)
                    viewModel.insertList(ListNodes(listNodesNames = list))
                }
            }
        })
    }


}