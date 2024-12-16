package com.first.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.first.foodapp.databinding.MealItemBinding
import com.first.foodapp.pojo.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoritesMealsViewHolder>() {



    inner class FavoritesMealsViewHolder(val binding : MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealsViewHolder {
        return FavoritesMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsViewHolder, position: Int) {
        val meal = differ.currentList[position]

        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(meal.strMealThumb)
                .into(imgMeal)

            tvMealName.text = meal.strMeal
        }


    }
}