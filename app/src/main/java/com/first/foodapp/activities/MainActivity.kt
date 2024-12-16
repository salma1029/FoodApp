package com.first.foodapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.first.foodapp.R
import com.first.foodapp.databinding.ActivityMainBinding
import com.first.foodapp.db.MealDatabase
import com.first.foodapp.viewModel.HomeViewModel
import com.first.foodapp.viewModel.HomeViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelProvider = HomeViewModelProvider(mealDatabase)
        ViewModelProvider(this,viewModelProvider)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btn_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}