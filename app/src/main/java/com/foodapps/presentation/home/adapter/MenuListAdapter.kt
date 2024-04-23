package com.foodapps.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapps.data.model.Menu
import com.foodapps.databinding.ItemMenuBinding
import com.foodapps.presentation.home.utils.LayoutManagerType
import com.foodapps.utils.toDollarFormat

class MenuListAdapter(
    private val menuList: List<Menu>,
    private var layoutManagerType: LayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER,
    private val onItemClick: (Menu) -> Unit) :
    RecyclerView.Adapter<MenuListAdapter.ItemMenuViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Menu>() {
                override fun areItemsTheSame(
                    oldItem: Menu,
                    newItem: Menu
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Menu,
                    newItem: Menu
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<Menu>) {
        dataDiffer.submitList(data)
    }

    fun setLayoutManagerType(layoutManagerType: LayoutManagerType) {
        this.layoutManagerType = layoutManagerType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemMenuViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemMenuViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    inner class ItemMenuViewHolder(
        private val binding: ItemMenuBinding,
        val itemClick: (Menu) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Menu) {
            with(item) {
               Glide.with(binding.root).load(imgUrl).into(binding.imageViewMenu)
                binding.TvMenuName.text = name
                binding.TvMenuPrice.text = price.toDollarFormat()
                itemView.setOnClickListener { itemClick(this) }

            }
        }
    }
}
