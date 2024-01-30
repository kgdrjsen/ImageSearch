package com.android.imagesearch.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.imagesearch.data.Document
import com.android.imagesearch.data.MyItems
import com.android.imagesearch.databinding.ListItemBinding
import com.bumptech.glide.Glide

//밑에서 메인에서 받아오는 걸로 한번...
//class MyItemsAdapter (private var items : List<Document>) :
//    RecyclerView.Adapter<MyItemsAdapter.MyItemViewHolder>() {
//
//    private var myItemList = mutableListOf<MyItems>()
//
//    inner class MyItemViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        val img = binding.ivItem
//        val locate = binding.tvImgLocation
//        val date = binding.tvDate
//    }
//
//    interface ItemClick {
//        fun onClick(view: View, position: Int)
//    }
//
//    var itmeClick : ItemClick? = null
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
//        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyItemViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
//        Log.d("MyItemsAdapter", "#aaa onBindViewHolder() position = $position")
//        holder.itemView.setOnClickListener {
//            itmeClick?.onClick(it,position)
//        }
//        Glide.with(holder.itemView.context)
//            .load(items[position].thumbnail_url)
//            .into(holder.img)
//        holder.date.text = items[position].datetime
//            .substring(0,19)
//            .replace("T"," ")
//        holder.locate.text = items[position].display_sitename
//        val loc = items[position].display_sitename
//        Log.d("MyItemsAdapter","#aaa $loc")
//
//
//    }
//
//    fun like(item : MyItems) {
//        if (!myItemList.contains(item)) {
//            myItemList.add(item)
//        }
//    }
//    fun unLike(item: MyItems) {
//        if (myItemList.contains(item)) {
//            myItemList.remove(item)
//        }
//    }
//}

class MyItemsAdapter (var myItemList : List<MyItems>) :
    RecyclerView.Adapter<MyItemsAdapter.MyItemViewHolder>() {

//    private var myItemList = mutableListOf<MyItems>()


    inner class MyItemViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.ivItem
        val locate = binding.tvImgLocation
        val date = binding.tvDate
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itmeClick : ItemClick? = null

    override fun getItemCount(): Int {
        return myItemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        Log.d("MyItemsAdapter", "#aaa onBindViewHolder() position = $position")
        holder.itemView.setOnClickListener {
            itmeClick?.onClick(it,position)
        }

        Glide.with(holder.itemView.context)
            .load(myItemList[position].imageUrl)
            .into(holder.img)
        holder.locate.text = myItemList[position].location
        holder.date.text = myItemList[position].time
            .substring(0,19)
            .replace("T"," ")

        val loc = myItemList[position].location
        Log.d("MyItemsAdapter","#aaa $loc")



    }

}