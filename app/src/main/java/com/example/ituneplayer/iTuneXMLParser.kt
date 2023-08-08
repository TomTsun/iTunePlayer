package com.example.ituneplayer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.Settings.Global
import android.util.Log
import android.util.Xml
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class iTuneXMLParser() {
    private val factory = XmlPullParserFactory.newInstance() //no worry the version even OS
    private val parser = factory.newPullParser()
    private val data= mutableListOf<SongData>()
    private var imageFound = false

    suspend fun parseURL(url: String): List<SongData>{
        var text = ""
        var songTitle = ""
        var songCover:Bitmap? = null
        var songUrl=""

        withContext(Dispatchers.IO) {
            try{
                val inputStream = URL(url).openStream()
                parser.setInput(inputStream, null)
                var eventType = parser.next()

                while(eventType != XmlPullParser.END_DOCUMENT){
                    var tagName = parser.name
                    if(tagName.equals("entry", ignoreCase = true) && eventType == XmlPullParser.START_TAG) {
                        while(!(tagName.equals("entry", ignoreCase = true) && eventType == XmlPullParser.END_TAG)){
                            when(eventType){
                                XmlPullParser.START_TAG->if(tagName.equals("im:image", ignoreCase = true)){
                                    imageFound =parser.getAttributeValue(null, "height").equals("170")
                                }else if(tagName.equals("link", ignoreCase = true)){
                                    if(parser.getAttributeValue(null, "type").equals("audio/x-m4a")) {
                                        songUrl = parser.getAttributeValue(null, "href")
                                    }
                                }

                                XmlPullParser.TEXT->text=parser.text
                                XmlPullParser.END_TAG->if(tagName.equals("title", ignoreCase = true)){
                                    songTitle = text
                                }else if (tagName.equals("im:image", ignoreCase = true) && imageFound){
                                    val url = text
                                    Log.i("URL", url)
                                    val inputStream = URL(url).openStream()
                                    songCover = BitmapFactory.decodeStream(inputStream)
                                    imageFound = false
                                }
                            }
                            eventType=parser.next()
                            tagName=parser.name
                        }
                        data.add(SongData(songTitle, songCover, songUrl))
                        Log.i("title", songTitle)
                        Log.i("m4a", songUrl)

                    }
                    eventType = parser.next()
                }
            } catch (e: Throwable){
                e.printStackTrace()
            }
        }
        return data
    }
}