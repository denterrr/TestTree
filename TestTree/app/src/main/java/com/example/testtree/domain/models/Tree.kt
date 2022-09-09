package com.example.testtree.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "my_table")
data class Tree (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    var root: Node? = null
        )

