package com.example.testtree.data.node_db

import androidx.lifecycle.LiveData
import com.example.testtree.domain.repositories.MyRepositoryInterface
import com.example.testtree.domain.models.Node
import javax.inject.Inject

class MyRepository @Inject constructor(private val dao: MyDao) : MyRepositoryInterface {
    override suspend fun insertTree(model: Node) {
        dao.insert(model)
    }

    override suspend fun deleteNode(model: Node) {
        dao.delete(model)
    }

    override val trees: LiveData<List<Node>>
        get() = dao.getTree()

}