package com.example.testtree.di

import android.content.Context
import com.example.testtree.data.MyDao
import com.example.testtree.data.MyDatabase
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


}