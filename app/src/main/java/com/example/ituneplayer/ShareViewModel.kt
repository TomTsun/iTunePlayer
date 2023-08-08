package com.example.ituneplayer

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ShareViewModel(app: Application) : AndroidViewModel(app) {
    private val songDatabaseDao: SongDatabaseDao =
        SongDatabase.getInstance(app.applicationContext).databaseDao

    val songList by lazy {
        MutableLiveData<List<SongData>>().also {//return不是最後一行
            loadList()
        }
    }
    val isRefreshing = MutableLiveData<Boolean>()

    val selectedSong = MutableLiveData<SongData>()
    var position = 0
    var isPlaying = false
    var bufferPercentage = 0

    fun selectSong(index: Int) {
        songList.value?.let {//not null
            if (index >= 0 && index < it.size) {
                position = 0
                selectedSong.value = it[index]
            }
        }
    }

    public fun loadList() {//viewModel不會直接改ui，通常是藉由發送訊號的方式改變

        viewModelScope.launch {//viewmodel在，co-routine即可完成
            isRefreshing.value = true
            val list = songDatabaseDao.loadAll()
            if (list.size > 0) {
                songList.value = list
            } else {
//                songList.value =
//                    iTuneXMLParser().parseURL(
//                        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml"
//                    )
                val list = mutableListOf<SongData>()
                withContext(Dispatchers.IO){//背景執行
                    val response = iTuneApiService.api.getTopSongs(50).execute()
                    if(response.isSuccessful){
                        val body=response.body() as iTuneResponse
                        for(entry in body.feed.entries){
                            val title = entry.title.label
                            val url = entry.link[1].attributes.href
                            val image = entry.image[2].url
                            val inputSream = URL(image).openStream()
                            val cover = BitmapFactory.decodeStream(inputSream)
                            list.add(SongData(title, cover, url))
                        }
                    }
                }



                songList.value = list
                songList.value?.let { songDatabaseDao.insertList(it) }
            }
            isRefreshing.value = false
        }
    }

    fun reloadList() = viewModelScope.launch {
        songList.value = listOf<SongData>()
        songDatabaseDao.clear()
        loadList()
    }
}