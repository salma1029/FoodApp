package com.first.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.first.foodapp.databinding.MealItemBinding
import com.first.foodapp.pojo.Category
import com.first.foodapp.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    var onItemClick : ((MealsByCategory) -> Unit) ? = null

    inner class CategoryMealsViewHolder(val binding : MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MealsByCategory>(){
        override fun areItemsTheSame(oldItem: MealsByCategory, newItem: MealsByCategory): Boolean {
           return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: MealsByCategory,
            newItem: MealsByCategory
        ): Boolean {
            return oldItem == newItem
        }


    }

    private val differ = AsyncListDiffer(this,diffUtil)

    fun submitList(newMealsList: ArrayList<MealsByCategory>){
        differ.submitList(newMealsList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val meal = differ.currentList[position]

        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(meal.strMealThumb)
                .into(imgMeal)

            tvMealName.text = meal.strMeal
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(meal)
        }
    }
}