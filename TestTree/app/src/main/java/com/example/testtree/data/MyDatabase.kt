package com.example.testtree.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtree.domain.models.Tree
import com.example.testtree.data.typeconverter.Converter
import com.example.testtree.data.typeconverter.GsonParser
import com.google.gson.Gson

@Database(entities = [Tree::class], version = 1)
@TypeConverters(Converter::class)
abstract class MyDatabase: RoomDatabase() {

    abstract fun getMyDao(): MyDao

    companion object{
        private var startDatabase: MyDatabase ?= null

        val converterfactory = Converter(GsonParser(Gson()))

        @Synchronized
        fun getInstance(context: Context): MyDatabase{
            return if(startDatabase==null){
                startDatabase = Room.databaseBuilder(context,MyDatabase::class.java,"db")
                    .addTypeConverter(converterfactory)
                    .fallbackToDestructiveMigration()
                    .build()
                startDatabase as MyDatabase
            }else{
                startDatabase as MyDatabase
            }
        }
    }
}