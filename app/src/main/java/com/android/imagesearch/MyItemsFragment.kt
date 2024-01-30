package com.android.imagesearch

import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.imagesearch.adapter.MyItemsAdapter
import com.android.imagesearch.adapter.SearchAdapter
import com.android.imagesearch.data.Document
import com.android.imagesearch.data.MyItems
import com.android.imagesearch.databinding.FragmentMyItemsBinding
import com.android.imagesearch.databinding.FragmentSearchBinding

class MyItemsFragment : Fragment() {
    private var _binding : FragmentMyItemsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
//    private var myList : List<MyItems> = listOf()
//    private var myList = (mContext as MainActivity).myItemList()
    private lateinit var myList : List<MyItems>
//    private var myItemsAdapter =  MyItemsAdapter(myList)
    private lateinit var myItemsAdapter : MyItemsAdapter



//    private var myItemList : List<MyItems>  = listOf()
//    private var items = mutableListOf<Document>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        Log.d("MyItemsFragment","#aaa onAttach")
        myList = (mContext as MainActivity).myItemList()
        myItemsAdapter = MyItemsAdapter(myList)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyItemsBinding.inflate(inflater,container,false)


        return binding.root
        Log.d("MyItemsFragment","#aaa onCreateView")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        //어댑터 연결, myList가 없으면 앱이 죽으므로 필요한 if구문
//        if (myList.size !=0) {
//            binding.myitemsRecyclerview.adapter = MyItemsAdapter(myList)
//            myItemsAdapter = MyItemsAdapter(myList).apply {
//                myList = myList.toMutableList()
//                val check = myList[0]
//                Log.d("MyItemsFragment","#aaa list = $check")
//            }
//        }


        //그리드뷰
        binding.myitemsRecyclerview.adapter = myItemsAdapter
        binding.myitemsRecyclerview.layoutManager = GridLayoutManager(requireContext(),2,
            GridLayoutManager.VERTICAL,false)



    }

    override fun onResume() {
        super.onResume()
        myItemsAdapter.myItemList = (mContext as MainActivity).myItemList()
        myItemsAdapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}