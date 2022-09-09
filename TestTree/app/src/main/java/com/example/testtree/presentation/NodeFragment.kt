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
import com.example.testtree.domain.models.Node
import com.example.testtree.domain.models.Tree
import javax.inject.Inject

class NodeFragment : Fragment() {

    lateinit var binding: FragmentNodeBinding

    lateinit var tree: Tree

    @Inject
    lateinit var viewModel: NodeViewModel

    lateinit var curNode: Node

    lateinit var navController: NavController

    var bundle = Bundle()

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
                    viewModel.changeNode(curNode.left)
                    bundle.putParcelable("key", curNode.left)
                    navController.navigate(R.id.action_nodeFragment_self, bundle)
                } else {
                    Toast.makeText(requireContext(), "Have not left child", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            rightBtn.setOnClickListener {
                if (curNode.right != null) {
                    viewModel.changeNode(curNode.right)
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
                    if (curNode.isLeft != null) {
                        val i = curNode.parent
                        i?.left = null
                        viewModel.changeNode(i)
                        bundle.putParcelable("deleteleft", curNode.parent)
                        navController.navigate(R.id.action_nodeFragment_self, bundle)
                    } else if (curNode.isRight != null) {
                        val i = curNode.parent
                        i?.right = null
                        viewModel.changeNode(i)
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
                    viewModel.changeNode(curNode.parent)
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
                if(curNode.isRoot) binding.rootTv.visibility = View.VISIBLE
            } else {
                viewModel.getTrees().observe(viewLifecycleOwner, Observer { listTrees ->
                    tree = if (listTrees.isNotEmpty()) {
                        Log.d("TAG", "list not empty")
                        listTrees.last()
                    } else {
                        Log.d("TAG", "list empty")
                        Tree()
                    }

                    if (tree.root == null) {
                        binding.rootTv.visibility = View.VISIBLE
                        Log.d("TAG", "root empty")
                        curNode = Node(isRoot = true)
                        tree.root = curNode
                        binding.nameNode.text = curNode.name
                        viewModel.insertTree(tree)
                    } else {
                        binding.rootTv.visibility = View.VISIBLE
                        Log.d("TAG", "root not empty")
                        curNode = tree.root!!
                        binding.nameNode.text = curNode.name
                    }
                })
            }
        } else {
            if (bundleNodeDeleteleft != null) {
                curNode = bundleNodeDeleteleft
                curNode.left = null
                binding.nameNode.text = curNode.name
                if(curNode.isRoot) binding.rootTv.visibility = View.VISIBLE
                Log.d("TAG", "We here")
            } else if (bundleNodeDeleteright != null) {
                curNode = bundleNodeDeleteright
                curNode.right = null
                binding.nameNode.text = curNode.name
                if(curNode.isRoot) binding.rootTv.visibility = View.VISIBLE
                Log.d("TAG", "We here")


            }
        }
    }


}