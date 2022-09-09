package com.example.testtree.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "list_table")
data class ListNodes(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo
    var listNodesNames: LinkedList<String>
)