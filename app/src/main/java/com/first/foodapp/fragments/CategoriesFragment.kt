package com.first.foodapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.first.foodapp.activities.MainActivity
import com.first.foodapp.adapters.CategoriesAdapter
import com.first.foodapp.databinding.FragmentCategoriesBinding
import com.first.foodapp.pojo.Category
import com.first.foodapp.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {
    private var _binding : FragmentCategoriesBinding? = null
    private val binding : FragmentCategoriesBinding
        get() = _binding!!
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeCategories()
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) {
            categoriesAdapter.submitList(it as ArrayList<Category>)
        }
    }

    private fun prepareRecyclerView() {
        binding.favoriteRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}