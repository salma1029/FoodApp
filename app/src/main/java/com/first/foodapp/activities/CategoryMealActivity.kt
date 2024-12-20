package com.first.foodapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.first.foodapp.R
import com.first.foodapp.adapters.CategoryMealsAdapter
import com.first.foodapp.databinding.ActivityCategoryMealBinding
import com.first.foodapp.fragments.HomeFragment
import com.first.foodapp.fragments.HomeFragment.Companion.MEAL_ID
import com.first.foodapp.fragments.HomeFragment.Companion.MEAL_NAME
import com.first.foodapp.fragments.HomeFragment.Companion.MEAL_THUMB
import com.first.foodapp.pojo.Meal
import com.first.foodapp.pojo.MealsByCategory
import com.first.foodapp.viewModel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCategoryMealBinding
    private val categoryMealViewModel by viewModels<CategoryMealsViewModel>()
    private lateinit var rvAdapter : CategoryMealsAdapter
    private lateinit var mealId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealViewModel.observeMealsLiveData().observe(this , Observer {

           rvAdapter.submitList(it as ArrayList<MealsByCategory>)
            binding.tvCategoryCount.text = rvAdapter.itemCount.toString()
        })

        onItemClick()

    }

    private fun prepareRecyclerView() {
        rvAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = rvAdapter
        }
    }

    private fun onItemClick() {
        rvAdapter.onItemClick = { meal ->
            val intent = Intent(this , MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }


}