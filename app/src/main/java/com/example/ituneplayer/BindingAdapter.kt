package com.example.ituneplayer

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SongData>?) {
    val adapter = recyclerView.adapter as? iTuneRecyclerViewAdapter
    if (adapter != null && data != null){
        adapter.song = data
    }
}