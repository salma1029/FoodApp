package com.first.foodapp.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.first.foodapp.activities.MainActivity
import com.first.foodapp.activities.MealActivity
import com.first.foodapp.databinding.FragmentMealBottomSheettBinding
import com.first.foodapp.fragments.HomeFragment
import com.first.foodapp.fragments.HomeFragment.Companion.MEAL_NAME
import com.first.foodapp.fragments.HomeFragment.Companion.MEAL_THUMB
import com.first.foodapp.pojo.Meal
import com.first.foodapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId : String? = null

    private lateinit var binding : FragmentMealBottomSheettBinding
    private lateinit var viewModel : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheettBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }
        observeBottomSheetMeal()
        onBottomSheetDialogClick()


    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            val intent = Intent(context , MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,mealId)
            intent.putExtra(MEAL_NAME,meal?.strMeal)
            intent.putExtra(MEAL_THUMB,meal?.strMealThumb)
            startActivity(intent)
        }
    }

    private var meal : Meal? = null

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMealLiveData().observe(viewLifecycleOwner
        ) {
            meal = it
            binding.tvBottomSheetCategory.text = it.strCategory
            binding.tvBottomSheetArea.text = it.strArea
            binding.tvCategoryBottomSheetName.text = it.strMeal
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgCategory)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}