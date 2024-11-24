package com.kubik.userappcourse.ui.base.base_basket.basket.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.ItemCartProductBinding
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import com.kubik.userappcourse.ui.getStatusProduct
import com.squareup.picasso.Picasso

class BasketAdapter(private val listener: BasketAdapterListener) :
    RecyclerView.Adapter<BasketAdapter.BasketHolder>() {

    private var list = listOf<GetBasketProductModel>()

    fun updateList(newList: List<GetBasketProductModel>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class BasketHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetBasketProductModel) {
            binding.apply {
                textName.text = item.title
                textCount.text = getStatusProduct(item.status, root.context)
                imageView.setImageResource(R.drawable.shopping_cart)
                if (item.imageUri != Uri.EMPTY) {
                    Picasso.get().load(item.imageUri).into(imageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder = BasketHolder(
        ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BasketHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.onClick(list[position])
        }
    }

}