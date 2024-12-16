package com.first.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.first.foodapp.pojo.Category
import com.first.foodapp.pojo.MealsByCategory
import com.first.foodapp.pojo.MealsByCategoryList
import com.first.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {
    private val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(category : String){
        RetrofitInstance.api.getMealsByCategory(category).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {
                    mealsLiveData.value = it.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("Category Meal Activity",t.message.toString())
            }

        })
    }

    fun observeMealsLiveData() : LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}