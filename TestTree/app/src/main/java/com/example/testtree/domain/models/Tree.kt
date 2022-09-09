package com.example.testtree.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize



data class Tree (

    val id: Int = 0,

    var root: Node? = null
        )

