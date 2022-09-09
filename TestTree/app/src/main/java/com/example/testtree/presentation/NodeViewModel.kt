package com.example.testtree.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtree.domain.MyRepositoryInterface
import com.example.testtree.domain.models.Node
import com.example.testtree.domain.models.Tree
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NodeViewModel @Inject constructor(private val repository: MyRepositoryInterface) :
    ViewModel() {

    private var _currentNode = MutableLiveData<Node?>()
    val currentNode : LiveData<Node?>
        get() = _currentNode

    var _tree = MutableLiveData<Tree>()


    val tree: LiveData<Tree>
        get() = _tree


    fun changeNode(node: Node?){
        _currentNode.postValue(node)
    }


    fun insertTree(model: Tree) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTree(model)
        }
    }


    fun getTrees(): LiveData<List<Tree>> {
        return repository.trees
    }


}