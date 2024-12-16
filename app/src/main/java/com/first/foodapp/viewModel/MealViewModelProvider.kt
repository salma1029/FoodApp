package com.first.foodapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.first.foodapp.db.MealDatabase

class MealViewModelProvider(
    private val mealDatabase : MealDatabase
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}