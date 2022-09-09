package com.example.testtree.data.node_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtree.data.typeconverter.Converter
import com.example.testtree.data.typeconverter.GsonParser
import com.example.testtree.domain.models.Node
import com.google.gson.Gson

@Database(entities = [Node::class], version = 2)
@TypeConverters(Converter::class)
abstract class MyDatabase: RoomDatabase() {

    abstract fun getMyDao(): MyDao

    companion object{
        private var startDatabase: MyDatabase?= null

        val converterfactory = Converter(GsonParser(Gson()))

        @Synchronized
        fun getInstance(context: Context): MyDatabase {
            return if(startDatabase ==null){
                startDatabase = Room.databaseBuilder(context, MyDatabase::class.java,"db2")
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