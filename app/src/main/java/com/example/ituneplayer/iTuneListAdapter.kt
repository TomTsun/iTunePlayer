package com.example.ituneplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class iTuneListAdapter(val context: Context):BaseAdapter() {
    var song = listOf<SongData>()
    set(value){
        field=value
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return song.size
    }

    override fun getItem(p0: Int): Any {
        return song[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.itune_list_item, null)
        val textView = itemView.findViewById<TextView>(R.id.textView)
        textView.text = song[p0].title
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(song[p0].cover)

        return itemView
    }

}