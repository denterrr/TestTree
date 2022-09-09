package com.example.testtree.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testtree.domain.models.Tree

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model: Tree)


    @Query("SELECT * from my_table")
    fun getTree(): LiveData<List<Tree>>

}