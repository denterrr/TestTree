package com.example.testtree.domain.repositories

import androidx.lifecycle.LiveData
import com.example.testtree.domain.models.ListNodes
import com.example.testtree.domain.models.Node

interface ListRepositoryInterface {

    suspend fun insert(model: ListNodes)
    suspend fun delete(model: ListNodes)
    val lists: LiveData<List<ListNodes>>
}