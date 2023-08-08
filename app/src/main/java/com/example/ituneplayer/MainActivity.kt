package com.example.ituneplayer

import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ituneplayer.databinding.ActivitySwipeRefreshBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //用onAttach
//        val listFragment=supportFragmentManager.findFragmentById(R.id.listFragment) as iTuneListFragment? //因為R.id.listFragment回傳是一個fragment
//        listFragment?.listener = object : iTuneListFragment.onSongSelectListener{
//            override fun onSongSelected(title: String, cover: Bitmap, url: String) {
//                val previewFragment=supportFragmentManager.findFragmentById(R.id.previewFragment) as iTunePreviewFragment?
//                previewFragment?.previewSong(title, cover, url, 0)
//            }
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }


    //    override fun onSongSelected(position: Int) {
//        val previewFragment=supportFragmentManager.findFragmentById(R.id.previewFragment) as iTunePreviewFragment?
//        if (previewFragment!=null) {
//            previewFragment?.previewSong(title, cover, url, 0)
//        }else{
//            val action=iTuneListFragmentDirections.actionITuneListFragmentToITunePreviewFragment(
//                title, cover, url)
//            findNavController(R.id.navHostFragment).navigate(action)
//
//        }
//    }
}