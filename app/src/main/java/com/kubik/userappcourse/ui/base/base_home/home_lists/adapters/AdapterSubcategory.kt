package com.kubik.userappcourse.ui.base.base_home.home_lists.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.ItemFilterBinding
import com.kubik.userappcourse.ui.base.models.SubcategoryModel

class AdapterSubcategory(private val listener: SubcategoryAdapterListener) :
    RecyclerView.Adapter<AdapterSubcategory.SubcategoryHolder>() {

    private var list = listOf<SubcategoryModel>()

    fun setList(newList: List<SubcategoryModel>, recyclerView: RecyclerView) {
        list = newList
        notifyDataSetChanged()
        recyclerView.scrollToPosition(1)
    }

    inner class SubcategoryHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SubcategoryModel, position: Int) {
            binding.apply {
                textName.text = item.name
                if (0 == position) {
                    textName.setBackgroundColor(root.context.getColor(R.color.item_adapter_background_down))
                    textName.setTextColor(root.context.getColor(R.color.black))
                } else {
                    textName.setBackgroundColor(root.context.getColor(R.color.item_adapter_background))
                    textName.setTextColor(root.context.getColor(R.color.white))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryHolder {
        return SubcategoryHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SubcategoryHolder, position: Int) {
        holder.bind(list[position], position)
        holder.itemView.setOnClickListener {
            listener.onClickItem(position)
            Log.d("MyLog", "AdapterSubcategory | on click item | position: ${position}")
        }
    }

}