package com.example.testtree.data.list_db

import androidx.lifecycle.LiveData
import com.example.testtree.domain.repositories.ListRepositoryInterface
import com.example.testtree.domain.models.ListNodes
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(val dao: ListDao): ListRepositoryInterface {
    override suspend fun insert(model: ListNodes) {
        dao.insert(model)
    }

    override suspend fun delete(model: ListNodes) {
        dao.delete(model)
    }

    override val lists: LiveData<List<ListNodes>>
        get() = dao.getList()
}