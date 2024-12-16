package com.first.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.first.foodapp.databinding.CategoryItemBinding
import com.first.foodapp.pojo.Category

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    var onItemClick : ((Category) -> Unit) ? = null

    inner class CategoriesViewHolder(val binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this,diffUtil)

    fun submitList(newMealsList: ArrayList<Category>){
        differ.submitList(newMealsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = differ.currentList[position]

        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(category.strCategoryThumb)
                .into(imgCategory)

            tvCategoryName.text = category.strCategory
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(category)
        }

    }
}