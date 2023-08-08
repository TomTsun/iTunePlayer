package com.example.ituneplayer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray{
        val os = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, os)
        return os.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}