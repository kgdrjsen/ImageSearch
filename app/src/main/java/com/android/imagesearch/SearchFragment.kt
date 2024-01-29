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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.adapter.SearchAdapter
import com.android.imagesearch.data.Document
import com.android.imagesearch.databinding.FragmentSearchBinding
import com.android.imagesearch.retrofit.NetWorkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notify

class SearchFragment : Fragment(){

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var items = mutableListOf<Document>()


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
        binding.recyclerview.adapter = SearchAdapter(items)
        //리사이클러뷰 그리드뷰로
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)

        val adapterItem = SearchAdapter(items)
        Log.d("SearchFragment","#aaa $adapterItem")



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
    private fun responseNetWork(param : String) = lifecycleScope.launch() {
        val restApi = "867c4ce6796c040ac81eded3a5037936"
        val authorization = "KakaoAK $restApi"
        val responseData = NetWorkClient.imgNetWork.getImg(authorization, param)
        items = responseData.documents.toMutableList()
        //데이터 들어오는지 확인용 로그
        val listItem = items[0]
        Log.d("SearchFragment","#aaa $listItem")

        withContext(Dispatchers.Main) {
            binding.recyclerview.adapter = SearchAdapter(items)
            binding.recyclerview.adapter?.notifyDataSetChanged()
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
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
            })
    }

}