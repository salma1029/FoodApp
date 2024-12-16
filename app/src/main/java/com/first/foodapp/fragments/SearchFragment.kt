package com.first.foodapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.first.foodapp.activities.MainActivity
import com.first.foodapp.adapters.MealsAdapter
import com.first.foodapp.databinding.FragmentSearchBinding
import com.first.foodapp.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding : FragmentSearchBinding
        get() = _binding!!
    private lateinit var viewModel : HomeViewModel
    private lateinit var searchedMealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        searchedMealsAdapter = MealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()

        binding.imgSearchArrow.setOnClickListener{
            searchMeals()
        }


        observeSearchedMealsLiveData()

        var searchJob : Job? = null
        binding.edSearchBox.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(it.toString())
            }
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner
        ) {
            searchedMealsAdapter.differ.submitList(it)
        }
    }

    private fun prepareRecyclerView() {
        binding.rvSearchedMeals.apply {
            layoutManager =GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchedMealsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}