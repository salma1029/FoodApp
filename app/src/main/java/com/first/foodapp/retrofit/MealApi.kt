package com.first.foodapp.retrofit

import com.first.foodapp.pojo.CategoryList
import com.first.foodapp.pojo.MealsByCategoryList
import com.first.foodapp.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {

    @GET("random.php")
    fun getRandomMeals() : Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(
        @Query("i")
        id : String
    ): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(
        @Query("c")
        categoryName : String
    ): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c")
        categoryName : String
    ): Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(
        @Query("s")
        searchQuery : String
    ): Call<MealList>
}