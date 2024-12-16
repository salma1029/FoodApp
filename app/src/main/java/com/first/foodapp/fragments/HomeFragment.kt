package com.first.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.first.foodapp.R
import com.first.foodapp.activities.CategoryMealActivity
import com.first.foodapp.activities.MainActivity
import com.first.foodapp.activities.MealActivity
import com.first.foodapp.adapters.CategoriesAdapter
import com.first.foodapp.adapters.MostPopularAdapter
import com.first.foodapp.bottomSheet.MealBottomSheetFragment
import com.first.foodapp.databinding.FragmentHomeBinding
import com.first.foodapp.pojo.Category
import com.first.foodapp.pojo.MealsByCategory
import com.first.foodapp.pojo.Meal
import com.first.foodapp.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? =null
    private val binding : FragmentHomeBinding
        get() = _binding!!
    private lateinit var viewModel : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.first.foodapp.fragments.idMeal"
        const val MEAL_NAME= "com.first.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.first.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME=" com.example.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemRecyclerView()
        prepareCategoriesItemRecyclerView()

        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItems()

        onPopularItemClick()

        viewModel.getCategories()
        observeCategories()

        onCategoryClick()

        onPopularItemLongClick()

        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onItemLongClick = { meal ->
           val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick ={ category ->
            val intent = Intent(activity , CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) {
            categoriesAdapter.submitList(it as ArrayList<Category>)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity , MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun prepareCategoriesItemRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }


    private fun observePopularItems() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) {
            popularItemsAdapter.submitList(it as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal(){
        viewModel.observeRandomLiveData().observe(viewLifecycleOwner
        ) {
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb).into(binding.imgRandomMeal)

            this.randomMeal = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}