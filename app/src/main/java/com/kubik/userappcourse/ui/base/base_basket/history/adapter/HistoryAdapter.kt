package com.kubik.userappcourse.ui.base.base_basket.history.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubik.userappcourse.databinding.ItemHistoryProductBinding
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import com.squareup.picasso.Picasso

class HistoryAdapter(private val listener: HistoryAdapterListener) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    private var list = listOf<GetBasketProductModel>()

    fun updateList(newList: List<GetBasketProductModel>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class HistoryHolder(private val binding: ItemHistoryProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetBasketProductModel) {
            binding.apply {
                textTitle.text = item.title
                if (item.imageUri != Uri.EMPTY) {
                    Picasso.get().load(item.imageUri).into(imageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder =
        HistoryHolder(
            ItemHistoryProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.onClick(list[position])
        }
    }

}