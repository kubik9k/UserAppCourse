package com.kubik.userappcourse.ui.base.base_home.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubik.userappcourse.databinding.ItemHomeAdapterBinding
import com.kubik.userappcourse.ui.base.models.HomeModel

class HomeAdapter(private val listener: HomeAdapterListener) :
    RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private var list = listOf<HomeModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: List<HomeModel>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class HomeHolder(private val binding: ItemHomeAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeModel) {
            binding.apply {
                nameCategory.text = item.name
                image.setImageResource(item.imgId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            ItemHomeAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.onClickAdapter(position)
        }
    }

}