package com.example.testtree.di

import com.example.testtree.domain.repositories.MyRepositoryInterface
import com.example.testtree.data.node_db.MyRepository
import com.example.testtree.data.list_db.ListRepositoryImpl
import com.example.testtree.domain.repositories.ListRepositoryInterface
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun bindrepo(repo: MyRepository): MyRepositoryInterface

    @Binds
    fun bindrepo2(repo: ListRepositoryImpl): ListRepositoryInterface
}