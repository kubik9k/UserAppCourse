package com.kubik.userappcourse.ui.base.base_home.home_lists.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.ItemCartProductBinding
import com.kubik.userappcourse.ui.base.models.GetProductModel
import com.squareup.picasso.Picasso

class AdapterCartProduct(private val listener: CartProductListener) :
    RecyclerView.Adapter<AdapterCartProduct.ProductViewHolder>() {

    private var list = listOf<GetProductModel>()

    fun updateList(newList: List<GetProductModel>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetProductModel) {
            binding.apply {
                textName.text = item.title
                textCount.text = "${root.context.getString(R.string.price)}: ${item.price} $"
                imageView.setImageResource(R.drawable.shopping_cart)
                if (item.imageUri != Uri.EMPTY) {
                    Picasso.get().load(item.imageUri).into(imageView)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.setOnClickListener {
            listener.onClickProduct(list[position])
        }
    }

}