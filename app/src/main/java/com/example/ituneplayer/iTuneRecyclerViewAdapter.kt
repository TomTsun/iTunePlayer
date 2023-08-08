package com.example.ituneplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ituneplayer.databinding.ItuneListItemBinding

class iTuneRecyclerViewAdapter(
    data: List<SongData>,
    val listener: RecyclerViewClickListener)
    : RecyclerView.Adapter<iTuneRecyclerViewAdapter.viewHolder>() {

    var song = listOf<SongData>()
    set(value){
        field=value
        notifyDataSetChanged()
    }

    interface RecyclerViewClickListener{
        fun onItemClick(view: View, position: Int)
    }

    class  viewHolder(val binding: ItuneListItemBinding): RecyclerView.ViewHolder(binding.root){
//        val textView = view. findViewById<TextView>(R.id.textView)
//        val imageView = view.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.itune_list_item, parent, false) // attachToRoot是否要馬上attach，但這邊沒有馬上要show資料，所以false
//
//        return viewHolder(view)//viewHolder不須知道listener
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItuneListItemBinding.inflate(inflater)

        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return song.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
//        holder.binding.textView.text = song[position].title
//        holder.binding.imageView.setImageBitmap(song[position].cover) //不要再塞到這邊
        holder.binding.songData=song[position]
        holder.binding.root.setOnClickListener{
            listener.onItemClick(it, position)
        }
//        holder.binding.executePendingBindings()
    }
}