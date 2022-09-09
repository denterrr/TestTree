package com.example.testtree.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtree.data.list_db.ListRepositoryImpl
import com.example.testtree.domain.repositories.MyRepositoryInterface
import com.example.testtree.domain.models.ListNodes
import com.example.testtree.domain.models.Node
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NodeViewModel @Inject constructor(
    private val repository: MyRepositoryInterface,
    private val listRepositoryImpl: ListRepositoryImpl
) : ViewModel() {


    fun insertTree(model: Node) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTree(model)
        }
    }

    fun deleteNode(model: Node) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNode(model)
        }
    }

    fun getTrees(): LiveData<List<Node>> {
        return repository.trees
    }

    fun insertList(model: ListNodes){
        viewModelScope.launch(Dispatchers.IO){
            listRepositoryImpl.insert(model)
        }
    }

    fun deleteList(model: ListNodes){
        viewModelScope.launch(Dispatchers.IO){
            listRepositoryImpl.delete(model)
        }
    }

    fun getListNodes(): LiveData<List<ListNodes>>{
        return listRepositoryImpl.lists
    }


}