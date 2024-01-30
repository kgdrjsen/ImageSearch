package com.android.imagesearch


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.adapter.SearchAdapter
import com.android.imagesearch.data.Document
import com.android.imagesearch.data.Image
import com.android.imagesearch.data.MyItems
import com.android.imagesearch.databinding.FragmentSearchBinding
import com.android.imagesearch.retrofit.NetWorkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var items = mutableListOf<Document>()
    private lateinit var mContext: MainActivity
    private var mAdapter = SearchAdapter(items)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding.toolbar
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        setOnQueryTextListenter()
        listInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //데이터 누수 방지
        _binding = null
    }

    //api요청
    private fun responseNetWork(search: String) = lifecycleScope.launch() {
        val restApi = "867c4ce6796c040ac81eded3a5037936"
        val authorization = "KakaoAK $restApi"
        val responseData = NetWorkClient.imgNetWork.getImg(authorization, search)

        items = responseData.documents.toMutableList()

        //코루틴 메인쓰레드추가
        withContext(Dispatchers.Main) {
            mAdapter.items = items
            //noinspection NotifyDataSetChanged
            binding.recyclerview.adapter?.notifyDataSetChanged()
        }
    }

    private fun listInit() {
        //어댑터 연결
        binding.recyclerview.adapter = mAdapter
        //리사이클러뷰 그리드뷰로
        binding.recyclerview.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        val adapterItem = SearchAdapter(items)
        Log.d("SearchFragment", "#aaa gridView = $adapterItem")

        clickItem()
    }

    private fun clickItem() {
        object : SearchAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("SearchFragment", "#aaa onClick")
                val loc = items[position].display_sitename
                val time = items[position].datetime
                val url = items[position].thumbnail_url
                val item = MyItems(loc, time, url)

                //메인 리스트 가져오기
                var myList = mContext.myItemList()

                if (!myList.contains(item)) {
                    mContext.like(item)
                    Log.d("SearchFragment", "#aaa likecheck = $item")

                } else {
                    mContext.unLike(item)
                    Log.d("SearchFragment", "#aaa likecheck = $item")
                }
            }
        }.also { mAdapter.itemClick = it }
    }

    //검색기능
    private fun setOnQueryTextListenter() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                responseNetWork(query ?: "")
                Log.d("SearchFragment", "#aaa search = $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}

