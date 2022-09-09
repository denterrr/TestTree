package com.example.testtree.domain

import androidx.lifecycle.LiveData
import com.example.testtree.domain.models.Tree

interface MyRepositoryInterface {
    suspend fun insertTree(model: Tree)
    val trees: LiveData<List<Tree>>
}