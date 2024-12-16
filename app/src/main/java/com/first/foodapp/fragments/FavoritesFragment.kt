package com.first.foodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.first.foodapp.activities.MainActivity
import com.first.foodapp.adapters.MealsAdapter
import com.first.foodapp.databinding.FragmentFavoritesBinding
import com.first.foodapp.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {
    private var _binding : FragmentFavoritesBinding? = null
    private val binding : FragmentFavoritesBinding
        get() = _binding!!
    private lateinit var viewModel : HomeViewModel
    private lateinit var rvAdapter : MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        rvAdapter = MealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeFavourites()
        prepareRecyclerView()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favoriteMeal =  rvAdapter.differ.currentList[position]
                viewModel.deleteMeal(favoriteMeal)
                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        viewModel.insertMeal(favoriteMeal)
                    }
                ).show()

            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }

    private fun prepareRecyclerView() {
        binding.favRecView.apply {
            layoutManager =GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
                adapter = rvAdapter
        }
    }

    private fun observeFavourites() {
        viewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer {
            rvAdapter.differ.submitList(it)
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}