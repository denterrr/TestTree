package com.example.testtree.di

import android.content.Context
import com.example.testtree.presentation.MainActivity
import com.example.testtree.presentation.NodeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, RepoModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(nodeFragment: NodeFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context) : Builder

        fun build(): AppComponent
    }

}