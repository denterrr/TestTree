package com.example.testtree.di

import com.example.testtree.domain.MyRepositoryInterface
import com.example.testtree.data.MyRepository
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun bindrepo(repo: MyRepository): MyRepositoryInterface
}