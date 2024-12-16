package com.first.foodapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.UriPermission
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.first.foodapp.R
import com.first.foodapp.databinding.ActivityMealBinding
import com.first.foodapp.db.MealDatabase
import com.first.foodapp.fragments.HomeFragment
import com.first.foodapp.pojo.Meal
import com.first.foodapp.viewModel.HomeViewModel
import com.first.foodapp.viewModel.MealViewModel
import com.first.foodapp.viewModel.MealViewModelProvider

class MealActivity : AppCompatActivity() {
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var binding : ActivityMealBinding
    private lateinit var youtubeLink : String
    private lateinit var mealViewModel : MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelProvider = MealViewModelProvider(mealDatabase)
        mealViewModel = ViewModelProvider(this,viewModelProvider)[MealViewModel::class.java]

        getInformationFromIntent()

        setInformationToViews()

        loadingCase()
        mealViewModel.getMealDetails(mealId)
        observeMealDetailsLiveData()

        onYoutubeImageClick()
        onFavouriteClick()

    }

    private fun onFavouriteClick() {
        binding.btnSave.setOnClickListener {
            mealToSave?.let {
                mealViewModel.insertMeal(it)
            }
        }
    }

    private var mealToSave : Meal? = null
    @SuppressLint("SetTextI18n")
    private fun observeMealDetailsLiveData() {
        mealViewModel.observeMealDetailsLiveData().observe(this
        ) {
            onResponseCase()
            val meal = it
            mealToSave = meal

            binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
            binding.tvAreaInfo.text = "Area : ${meal.strArea}"
            binding.tvInstructions.text = meal.strInstructions

            youtubeLink = meal.strYoutube!!
        }
    }


    private fun setInformationToViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this,R.color.white))
    }

    private fun getInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvContent.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvContent.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

    private fun onYoutubeImageClick(){
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
}