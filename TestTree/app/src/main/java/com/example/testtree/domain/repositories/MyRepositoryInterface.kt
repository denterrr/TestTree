package com.example.testtree.domain.repositories

import androidx.lifecycle.LiveData
import com.example.testtree.domain.models.Node
import com.example.testtree.domain.models.Tree

interface MyRepositoryInterface {
    suspend fun insertTree(model: Node)
    suspend fun deleteNode(model: Node)
    val trees: LiveData<List<Node>>
}