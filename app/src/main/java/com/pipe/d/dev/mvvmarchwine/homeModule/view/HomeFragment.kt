package com.pipe.d.dev.mvvmarchwine.homeModule.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pipe.d.dev.mvvmarchwine.BR
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.OnClickListener
import com.pipe.d.dev.mvvmarchwine.common.view.WineBaseFragment
import com.pipe.d.dev.mvvmarchwine.homeModule.model.HomeRepository
import com.pipe.d.dev.mvvmarchwine.homeModule.model.RoomDatabase
import com.pipe.d.dev.mvvmarchwine.homeModule.model.WineService
import com.pipe.d.dev.mvvmarchwine.homeModule.viewModel.HomeViewModel
import com.pipe.d.dev.mvvmarchwine.homeModule.viewModel.HomeViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/****
 * Project: Wines
 * From: com.cursosant.wines
 * Created by Alain Nicolás Tello on 06/02/24 at 20:23
 * All rights reserved 2024.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
class HomeFragment : WineBaseFragment(), OnClickListener {

    private lateinit var adapter: WineListAdapter
    private lateinit var vm: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this, HomeViewModelFactory(
            HomeRepository(RoomDatabase(), WineService())))[HomeViewModel::class.java]
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let {vm ->
            vm.snackBarMsg.observe(viewLifecycleOwner) {resMsg ->
                showMsg(resMsg)
            }
            vm.wines.observe(viewLifecycleOwner) {wines ->
                adapter.submitList(wines)
            }
        }
    }

    private fun setupAdapter() {
        adapter = WineListAdapter()
        adapter.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
            adapter = this@HomeFragment.adapter
        }
    }


    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    /*
    * OnClickListener
    * */
    override fun onFavorite(wine: Wine) {
        return
    }

    override fun onLongClick(wine: Wine) {
        val items = resources.getStringArray(R.array.array_home_options)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.home_dialog_title)
            .setItems(items) { _, index ->
                when(index) {
                    0 -> addToFavourites(wine)
                }
            }.show()
    }

    private fun addToFavourites(wine: Wine) {
        lifecycleScope.launch(Dispatchers.IO) {
            wine.isFavorite = true
            vm.addWine(wine)
        }
    }
}