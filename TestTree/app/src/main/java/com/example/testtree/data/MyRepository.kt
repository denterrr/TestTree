package com.example.testtree.data

import androidx.lifecycle.LiveData
import com.example.testtree.domain.MyRepositoryInterface
import com.example.testtree.domain.models.Tree
import javax.inject.Inject

class MyRepository @Inject constructor(private val dao: MyDao): MyRepositoryInterface {
    override suspend fun insertTree(model: Tree) {
        dao.insert(model)
    }

    override val trees: LiveData<List<Tree>>
        get() = dao.getTree()

}