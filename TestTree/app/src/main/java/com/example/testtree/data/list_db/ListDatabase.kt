package com.example.testtree.data.list_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtree.data.typeconverter.GsonParser
import com.example.testtree.data.typeconverter.ListConverter
import com.example.testtree.domain.models.ListNodes
import com.google.gson.Gson

@Database(entities = [ListNodes::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class ListDatabase: RoomDatabase() {

    abstract fun getMyDao(): ListDao

    companion object{
        private var startDatabase: ListDatabase ?= null

        private val converterfactory = ListConverter(GsonParser(Gson()))

        @Synchronized
        fun getInstance(context: Context): ListDatabase{
            return if(startDatabase==null){
                startDatabase = Room.databaseBuilder(context,ListDatabase::class.java,"db3")
                    .addTypeConverter(converterfactory)
                    .fallbackToDestructiveMigration()
                    .build()
                startDatabase as ListDatabase
            }else{
                startDatabase as ListDatabase
            }
        }
    }
}