package com.example.testtree.domain.models


import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.random.Random

@Parcelize
class Node(
    var parent: Node? = null,
    var left: Node? = null,
    var right: Node? = null,
    var isRoot: Boolean = false,
    var isLeft: Boolean? = null,
    var isRight: Boolean? = null,
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