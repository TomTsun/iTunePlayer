package com.example.ituneplayer

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs_table")
data class SongData(
    val title: String = "",
    val cover: Bitmap? = null,
    val url: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}