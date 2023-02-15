package com.udacity.asteroidradar.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.ui.adapter.AsteroidAdapter
import com.udacity.asteroidradar.ui.viewmodel.MainViewModel
import com.udacity.asteroidradar.ui.viewmodel.MainViewModelFactory
import com.udacity.asteroidradar.util.AsteroidFilter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        initMenu()

        val adapter = AsteroidAdapter {asteroid ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(asteroid)
            findNavController().navigate(action)
        }

        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner) {asteroids ->
            adapter.submitList(asteroids)
        }

        viewModel.pictureOfDay.observe(viewLifecycleOwner) {pictureOfDay ->
           pictureOfDay?.let {
               Picasso.with(requireContext())
                   .load(pictureOfDay.url)
                   .into(binding.activityMainImageOfTheDay)
               binding.textView.text = pictureOfDay.title
           }
        }

        return binding.root
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_item_saved -> viewModel.updateAsteroidFilter(AsteroidFilter.ALL)
                    R.id.menu_item_today -> viewModel.updateAsteroidFilter(AsteroidFilter.TODAY)
                    R.id.menu_item_week -> viewModel.updateAsteroidFilter(AsteroidFilter.WEEK)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}
