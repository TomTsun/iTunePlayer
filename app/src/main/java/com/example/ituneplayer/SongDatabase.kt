package com.example.ituneplayer

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SongData::class], version = 1)
@TypeConverters(Converters::class)//已經有define
abstract class SongDatabase: RoomDatabase(){
    abstract val databaseDao: SongDatabaseDao
    companion object {
        var INSTANCE: SongDatabase? = null
        fun getInstance(context : Context): SongDatabase {
            if(INSTANCE == null){
                // use database builder to build a database obj
                //跟application的lifetime綁在一起
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,//可以access系統的窗口
                    SongDatabase::class.java,
                    "Songs.db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}