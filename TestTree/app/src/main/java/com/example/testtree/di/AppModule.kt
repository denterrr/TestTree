package com.example.testtree.di

import android.content.Context
import com.example.testtree.data.node_db.MyDao
import com.example.testtree.data.node_db.MyDatabase
import com.example.testtree.data.list_db.ListDao
import com.example.testtree.data.list_db.ListDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDao(context: Context): MyDao {
        return MyDatabase.getInstance(context).getMyDao()
    }

    @Provides
    @Singleton
    fun provideListDao(context: Context): ListDao {
        return ListDatabase.getInstance(context).getMyDao()
    }


}