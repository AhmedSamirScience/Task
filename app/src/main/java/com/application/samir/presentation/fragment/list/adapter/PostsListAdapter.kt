package com.application.samir.presentation.fragment.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.samir.data.remote.dto.PostDto
import com.application.samir.databinding.ItemPostBinding

import com.application.samir.presentation.fragment.list.clickEvent.PostListClickListener

class PostsListAdapter (private var myList: List<PostDto> = listOf()) : RecyclerView.Adapter<PostsListAdapter.ViewHolder>() {

    private var trxList: List<PostDto> = ArrayList()
    var postClickListener: PostListClickListener?= null
    private var parent: ViewGroup? = null


    init {
        this.trxList = myList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ItemPostBinding = ItemPostBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }
    override fun getItemCount(): Int {
        return trxList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.myItemTX = trxList[position]
        holder.bind(holder.myItemTX!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitMyList(myList: List<PostDto>, mainServiceClickListener: PostListClickListener) {
        this.trxList = myList
         this.postClickListener = mainServiceClickListener
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)  , View.OnClickListener{
        var myItemTX: PostDto? = null

        init {
            binding.cvContainer.setOnClickListener(this)
        }

        fun bind(myItem: PostDto?) {
            binding.myItem = myItem

        }

        override fun onClick(v: View?) {
            when(v) {
                binding.cvContainer -> {
                    myItemTX?.let { postClickListener?.onItemClicked(it) }
                }
            }
        }
    }

}