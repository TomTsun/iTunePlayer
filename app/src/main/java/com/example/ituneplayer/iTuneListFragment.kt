package com.example.ituneplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ituneplayer.databinding.FragmentItuneListBinding

class iTuneListFragment : Fragment(), iTuneRecyclerViewAdapter.RecyclerViewClickListener {
    private val model: ShareViewModel by activityViewModels()

//    interface onSongSelectListener{
//        fun onSongSelected(position: Int)
//    }

//    var listener:onSongSelectListener?=null


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        listener=context as onSongSelectListener//強迫host一定要implementonSongSelectListener
//    }

    lateinit var binding: FragmentItuneListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItuneListBinding.inflate(inflater) //僅能用default
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //每當view model改變，即被通知
        binding.lifecycleOwner = activity //binding物件可以成為LiveData的reserver
        binding.model = model

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        //透過 View model binding方式即可達成
//        swipeRefreshLayout.setOnRefreshListener {
////            loadList()    //already replaced moved to viewModel
//            model.loadList()
//        }

        model.loadList()

    }

    val adapter: iTuneRecyclerViewAdapter by lazy {
        //iTuneListAdapter(this)
        iTuneRecyclerViewAdapter(listOf<SongData>(), this)
    }

    val swipeRefreshLayout by lazy {
        binding.swipeRefreshLayout
    }


    override fun onItemClick(view: View, position: Int) {
//        listener?.onSongSelected(position)
        model.selectSong(position)

        if (parentFragment != null) {//on the phone page

            val action = iTuneListFragmentDirections.actionITuneListFragmentToITunePreviewFragment()
            findNavController().navigate(action)
        }
    }
}