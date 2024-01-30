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

class SearchFragment : Fragment(){

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var items = mutableListOf<Document>()
    private lateinit var mContext: Context
    private var mAdapter = SearchAdapter(items)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
        Log.d("SearchFragment","#aaa onCreateView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //툴바
        val toolbar = binding.toolbar
        val activity = activity as AppCompatActivity?
        activity?.setSupportActionBar(toolbar)

        //검색+데이터 불러오기까지하는 함수
        setOnQueryTextListenter()





        //뒤로가기?
        binding.btnBack.setOnClickListener {

        }
        //어댑터 연결
        binding.recyclerview.adapter = mAdapter

        //리사이클러뷰 그리드뷰로
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)

        val adapterItem = SearchAdapter(items)
        Log.d("SearchFragment","#aaa gridView = $adapterItem")



    }
    //뒤로가기(실패)
//    override fun onBackPressed() {
//        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        //데이터 누수 방지
        _binding = null
    }

    //api요청
    private fun responseNetWork(search : String) = lifecycleScope.launch() {
        val restApi = "867c4ce6796c040ac81eded3a5037936"
        val authorization = "KakaoAK $restApi"
        val responseData = NetWorkClient.imgNetWork.getImg(authorization, search)
        items = responseData.documents.toMutableList()
        //데이터 들어오는지 확인용 로그
        val listItem = items[0]
        Log.d("SearchFragment","#aaa item[0] = $listItem")

        //이런식으로 메인으로 넘기기
//        MyItems(listItem.display_sitename,listItem.datetime,listItem.thumbnail_url)
//        binding.recyclerview.adapter = SearchAdapter(items)
//        //억제주석
//        //noinspection NotifyDataSetChanged
//        binding.recyclerview.adapter?.notifyDataSetChanged()

        //코루틴 메인쓰레드추가
        withContext(Dispatchers.Main) {
            mAdapter.items = items
            //noinspection NotifyDataSetChanged
            binding.recyclerview.adapter?.notifyDataSetChanged()

        }

        mAdapter.itemClick = object : SearchAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Log.d("SearchFragment","#aaa onClick")
                val loc = items[position].display_sitename
                val time = items[position].datetime
                val url  = items[position].thumbnail_url
                val item = items[position]
                //메인 리스트 가져오기
                var myList = (mContext as MainActivity).myItemList()

//                MyItems(item.thumbnail_url,item.datetime,item.display_sitename)
//                if (!item.thumbnail_url.contains(MyItems(loc,time,url).imageUrl))
                if (items != myList){
//                    (mContext as MainActivity).like(MyItems(item.thumbnail_url,item.datetime,item.display_sitename))
                    val likeCheck = (mContext as MainActivity).like(MyItems(item.display_sitename,item.datetime,item.thumbnail_url))
                    Log.d("SearchFragment","#aaa likecheck = $likeCheck")

                }else{
//                    (mContext as MainActivity).unLike(MyItems(item.thumbnail_url,item.datetime,item.display_sitename))
                    val unLikeCheck = (mContext as MainActivity).unLike(MyItems(item.display_sitename,item.datetime,item.thumbnail_url))
                    Log.d("SearchFragment","#aaa likecheck = $unLikeCheck")
                }


            }
        }

    }

    private fun setUpImgDataParameter(query: String): Pair<String, String> {
        return "query" to query
    }


    //검색기능
    private fun setOnQueryTextListenter() {
        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                responseNetWork(query?: "")
                Log.d("SearchFragment","#aaa search = $query")


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
            })
    }

}

