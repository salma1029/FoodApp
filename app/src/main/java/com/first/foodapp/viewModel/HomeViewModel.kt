package com.first.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.first.foodapp.db.MealDatabase
import com.first.foodapp.pojo.Category
import com.first.foodapp.pojo.CategoryList
import com.first.foodapp.pojo.MealsByCategoryList
import com.first.foodapp.pojo.MealsByCategory
import com.first.foodapp.pojo.Meal
import com.first.foodapp.pojo.MealList
import com.first.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    val mealDatabase : MealDatabase
) : ViewModel() {
    private var randomMealLiveData  =MutableLiveData<Meal>()
    private var popularItemsLiveData  =MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData  =MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var searchedMealsLiveData = MutableLiveData<List<Meal>>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeals().enqueue(object  : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment ",t.message.toString())
            }

        })
    }

    fun observeRandomLiveData():LiveData<Meal>{
        return  randomMealLiveData
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body() != null){
                    popularItemsLiveData.value = response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("Home Fragment ",t.message.toString())
            }

        })
    }
    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return  popularItemsLiveData
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null){
                    categoriesLiveData.value = response.body()!!.categories
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home Fragment ",t.message.toString())
            }

        })
    }

    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return  categoriesLiveData
    }

    fun observeFavoritesMealsLiveData() : LiveData<List<Meal>>{
        return  favoritesMealsLiveData
    }

    fun getMealById(id : String) =
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let {
                  val mealList = response.body()?.meals
                    mealList?.let {
                        searchedMealsLiveData.value = it
                    }
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment ",t.message.toString())
            }

        })


    fun observeBottomSheetMealLiveData() : LiveData<Meal>{
        return  bottomSheetMealLiveData
    }

    fun deleteMeal(meal : Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }

    }

    fun searchMeals(mealQuery : String){
        RetrofitInstance.api.searchMeals(mealQuery).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let {
                    searchedMealsLiveData.value = it.meals
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment ",t.message.toString())
            }

        })
    }

    fun observeSearchedMealsLiveData() : LiveData<List<Meal>>{
        return searchedMealsLiveData
    }
}
