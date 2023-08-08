package com.example.ituneplayer

import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ituneplayer.databinding.FragmentItunePreviewBinding


class iTunePreviewFragment : Fragment(), MediaController.MediaPlayerControl {

    lateinit var binding: FragmentItunePreviewBinding

    private val mediaPlayer = MediaPlayer()
    private val mediaController: MediaController by lazy{
        object: MediaController(activity){
            override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
                if(event!!.keyCode== KeyEvent.KEYCODE_BACK){
                    if (parentFragment==null){
                        activity?.onBackPressed()
                    } else{
                        findNavController().popBackStack()
                    }

                }
                return super.dispatchKeyEvent(event)
            }

            override fun show(timeout: Int) {
                super.show(0)//不要隱藏
            }


        }
    }

    //因為沒有參數傳遞
//    val args:iTunePreviewFragmentArgs by navArgs<iTunePreviewFragmentArgs>()//

    private val model: ShareViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.selectedSong.observe(viewLifecycleOwner, Observer {
            previewSong(it.title, it.cover!!, it.url, model.position)
        })
//        parentFragment?.let{
//            if(savedInstanceState == null){
//                previewSong(args.title, args.cover, args.url, 0)
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentItunePreviewBinding.inflate(inflater)
        //return super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    fun previewSong(songTitle: String, songCover:Bitmap, songURL: String, position: Int){
        //已經存在model裡面
//        title=songTitle
//        cover=songCover
//        url=songURL

        binding.title=songTitle
        binding.cover=songCover

        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(songURL)
            mediaPlayer.setOnPreparedListener{
                Log.i("MediaPlayer", "I am ready")
                mediaPlayer.setOnCompletionListener {
                    model.isPlaying=false
                    mediaController.show()//強迫refresh畫面
                }
                mediaController.setAnchorView(binding.anchorView)
                mediaController.setMediaPlayer(this)//問題就是下面的function
                mediaController.show()
                if(model.position>0){
                    mediaPlayer.seekTo(position)
                }
                if(isPlaying){
                    mediaPlayer.start()
                }

            }
            mediaPlayer.setOnBufferingUpdateListener { mediaPlayer, i ->
                model.bufferPercentage=i
            }
            mediaPlayer.prepareAsync()
        }catch(e: Exception){
            e.printStackTrace()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putString("title", title)
//        outState.putParcelable("cover", cover)
//        outState.putString("url", url)
//        outState.putBoolean("isPlaying", isPlaying)
//        outState.putInt("currentPosition", mediaPlayer.currentPosition)
        model.position = mediaPlayer.currentPosition

    }


    //    fun onPreviewClicked(view: View) {
//        val intent= Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(intent)
//    }

    override fun onPause() {
        super.onPause()
        if(model.isPlaying) mediaPlayer.pause()
    }

    override fun onStart() {
        super.onStart()
        if(model.isPlaying) mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mediaController.hide()
    }

    override fun start() {
        mediaPlayer.start()
        model.isPlaying=true
    }

    override fun pause() {
        mediaPlayer.pause()
        model.isPlaying=false
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun getDuration(): Int {
        return mediaPlayer.duration
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun seekTo(p0: Int) {
        mediaPlayer.seekTo(p0)
    }

    override fun isPlaying(): Boolean {
        return model.isPlaying
    }

    override fun getBufferPercentage(): Int {
        return model.bufferPercentage
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getAudioSessionId(): Int {
        return mediaPlayer.audioSessionId
    }
}