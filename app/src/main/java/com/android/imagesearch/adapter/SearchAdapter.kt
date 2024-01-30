package com.android.imagesearch.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.resources.R
import androidx.recyclerview.widget.RecyclerView
import com.android.imagesearch.MainActivity
import com.android.imagesearch.data.Document
import com.android.imagesearch.data.MyItems
import com.android.imagesearch.databinding.ListItemBinding
import com.bumptech.glide.Glide
import java.time.format.DateTimeFormatter

//밑에 좋아요 기능을 메인액티비티를 통해 주고 받기? 해보기
class SearchAdapter(var items : List<Document>) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

//    private var myItemList = mutableListOf<MyItems>()

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }


    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("SearchAdapter", "#aaa onCreateViewHolder")
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("SearchAdapter", "#aaa onBindViewHolder() position = $position")
        holder.itemView.setOnClickListener {
            Log.d("SearchAdapter","#aaa onClick")
            itemClick?.onClick(it,position)
        }

        //섬네일 url 받아오기
        Glide.with(holder.itemView.context)
            .load(items[position].thumbnail_url)
            .into(holder.img)
        //datetime format이 안되서 substring으로 0~19번째까지 나타내고, 불필요한 T를 replace로 없애고 공백으로 바꾸기
        holder.date.text = items[position].datetime
            .substring(0,19)
            .replace("T"," ")
        holder.locate.text = items[position].display_sitename

        val loc = items[position].display_sitename
        Log.d("SearchAdapter","#aaa $loc")

//        //리사이클러뷰에 좋아요 표시 띄우기
//        if (myItemList[position].like) {
//            holder.like.setImageResource(com.android.imagesearch.R.drawable.star_full)
//        } else {
//            holder.like.setImageResource(com.android.imagesearch.R.drawable.star_empty)
//        }

    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    inner class ItemViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.ivItem
        val locate = binding.tvImgLocation
        val date = binding.tvDate
        val like = binding.ivFavorite
    }

}
//class SearchAdapter(private var items : List<Document>) :
//    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
//
//    private lateinit var mContext : Context
//
////    private var myItemList = mutableListOf<MyItems>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        Log.d("SearchAdapter", "#aaa onCreateViewHolder")
//        return ItemViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        Log.d("SearchAdapter", "#aaa onBindViewHolder() position = $position")
//
//        Glide.with(holder.itemView.context)
//            .load(items[position].thumbnail_url)
//            .into(holder.img)
//
//        holder.date.text = items[position].datetime
//            .substring(0,19)
//            .replace("T"," ")
//        holder.locate.text = items[position].display_sitename
//
//        val loc = items[position].display_sitename
//        Log.d("SearchAdapter","#aaa $loc")
//
//    }
//    override fun getItemCount(): Int {
//        return items.size
//    }
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    inner class ItemViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        val img = binding.ivItem
//        val locate = binding.tvImgLocation
//        val date = binding.tvDate
//        val like = binding.ivFavorite
//
//    }
//}

//class SearchAdapter(private val mContext : Context) :
//    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
//
//    var items = ArrayList<MyItems>()
//
//    fun clear() {
//        items.clear()
//        notifyDataSetChanged()
//    }
//
//    interface ItemClick {
//        fun onClick(view: View, position: Int)
//    }
//
//
//    var itmeClick : ItemClick? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        Log.d("SearchAdapter", "#aaa onCreateViewHolder")
//        return ItemViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        Log.d("SearchAdapter", "#aaa onBindViewHolder() position = $position")
//        holder.itemView.setOnClickListener {
//            itmeClick?.onClick(it,position)
//        }
//        Glide.with(mContext)
//            .load(items[position].imageUrl)
//            .into(holder.img)
//        holder.date.text = items[position].time
//            .substring(0,19)
//            .replace("T"," ")
//        holder.locate.text = items[position].location
//        holder.like.visibility = if (items[position].like) View.VISIBLE else View.GONE
//
//        Log.d("SearchAdapter","#aaa ")
//
//    }
//
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    inner class ItemViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        val img = binding.ivItem
//        val locate = binding.tvImgLocation
//        val date = binding.tvDate
//        val like = binding.ivFavorite
//    }
//
//}