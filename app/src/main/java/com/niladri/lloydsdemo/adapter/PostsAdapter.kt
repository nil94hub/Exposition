package com.niladri.lloydsdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.niladri.lloydsdemo.activities.MainActivity
import com.niladri.lloydsdemo.databinding.PostLayoutBinding
import com.niladri.lloydsdemo.interfaces.OnClick
import com.niladri.lloydsdemo.model.PostResponseItem
import com.niladri.lloydsdemo.model.PostResponse

class PostsAdapter(mainActivity: MainActivity) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private var data = ArrayList<PostResponseItem>()
    private var context: Context
    private lateinit var onClick: OnClick

    fun setPostData(postList: PostResponse, onClick: OnClick) {
        println("Nil cv1 -> " + postList)
        data = postList
        this.onClick = onClick
        notifyDataSetChanged()
    }

    init {
        this.context = mainActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutBinding = PostLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener(View.OnClickListener {
            onClick.passData(position)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(private val binding: PostLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostResponseItem) {
            binding.responseItem = item
        }
    }
}
