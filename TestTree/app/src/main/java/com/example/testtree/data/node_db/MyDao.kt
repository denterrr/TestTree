package com.example.testtree.data.node_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testtree.domain.models.Node
import com.example.testtree.domain.models.Tree

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model: Node)

    @Delete
    suspend fun delete(model: Node)


    @Query("SELECT * from my_table")
    fun getTree(): LiveData<List<Node>>

}