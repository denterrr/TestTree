package com.example.testtree.data.list_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testtree.domain.models.ListNodes
import com.example.testtree.domain.models.Node
import java.util.*

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(model: ListNodes)

    @Delete
    suspend fun delete(model: ListNodes)


    @Query("SELECT * from list_table")
    fun getList(): LiveData<List<ListNodes>>

}