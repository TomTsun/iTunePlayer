package com.example.ituneplayer

interface ParserListener {
    fun start()
    fun finish(songs: List<SongData>)
}