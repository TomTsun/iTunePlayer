package com.example.ituneplayer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SongDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<SongData>)
    @Query("SELECT * FROM songs_table")
    suspend fun loadAll(): List <SongData>
    @Query("DELETE FROM songs_table")
    suspend fun clear()
}