package com.first.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.first.foodapp.databinding.PopularItemsBinding
import com.first.foodapp.pojo.MealsByCategory

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.MostPopularViewHolder>() {
    var onItemClick : ((MealsByCategory) -> Unit) ? = null
    var onItemLongClick : ((MealsByCategory) -> Unit) ? = null

    inner class MostPopularViewHolder(val binding : PopularItemsBinding) : RecyclerView.ViewHolder(binding.root){


    }


    private val mealDiff = object : DiffUtil.ItemCallback<MealsByCategory>(){

        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this,mealDiff)

    fun submitList(newMealsList: List<MealsByCategory>) {
        differ.submitList(newMealsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularViewHolder {
        val binding = PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MostPopularViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MostPopularViewHolder, position: Int) {

        val meal = differ.currentList[position]

        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(meal.strMealThumb)
                .into(imgPopularMealItem)
        }

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(meal)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick!!.invoke(meal)
            true
        }

    }
}