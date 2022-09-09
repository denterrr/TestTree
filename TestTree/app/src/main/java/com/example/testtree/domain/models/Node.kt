package com.example.testtree.domain.models


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.random.Random

@Entity(tableName = "my_table")
@Parcelize
class Node(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var parent: Node? = null,
    @Transient
    var left: Node? = null,
    @Transient
    var right: Node? = null,
    @ColumnInfo
    var isRoot: Boolean = false,
    @ColumnInfo
    var isLeft: Boolean? = null,
    @ColumnInfo
    var isRight: Boolean? = null,
    @ColumnInfo
    var hash: Int = 0,
) : Parcelable {


    val name: String
        get() = BigInteger(
            1, MessageDigest.getInstance("SHA-256").digest(
                hashCode().toString()
                    .toByteArray()
            )
        ).toString(16).padStart(20, '0')

    override fun hashCode(): Int {
        if (hash == 0) {
            hash =
                Random.nextInt(10000) * Random.nextInt(10000) * Random.nextInt(10000) + Date().time.toInt()
        }
        return hash
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (name != other.name) return false
        if (parent != other.parent) return false
        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }
}