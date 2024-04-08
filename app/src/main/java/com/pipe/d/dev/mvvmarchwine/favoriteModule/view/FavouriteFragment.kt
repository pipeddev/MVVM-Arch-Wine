package com.pipe.d.dev.mvvmarchwine.favoriteModule.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pipe.d.dev.mvvmarchwine.BR
import com.pipe.d.dev.mvvmarchwine.common.entities.Wine
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.common.utils.OnClickListener
import com.pipe.d.dev.mvvmarchwine.common.view.WineBaseFragment
import com.pipe.d.dev.mvvmarchwine.favoriteModule.model.FavoriteRepository
import com.pipe.d.dev.mvvmarchwine.favoriteModule.model.RoomDatabase
import com.pipe.d.dev.mvvmarchwine.favoriteModule.viewModel.FavoriteViewModel
import com.pipe.d.dev.mvvmarchwine.favoriteModule.viewModel.FavoriteViewModelFactory
import com.pipe.d.dev.mvvmarchwine.updateModule.UpdateDialogFragment

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
class FavouriteFragment : WineBaseFragment(), OnClickListener {

    private lateinit var adapter: WineFavListAdapter
    private lateinit var vm: FavoriteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this, FavoriteViewModelFactory(
            FavoriteRepository(RoomDatabase())))[FavoriteViewModel::class.java]
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
        adapter = WineFavListAdapter()
        adapter.setOnClickListener(this)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@FavouriteFragment.adapter
        }
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    /*
    * OnClickListener
    * */
    override fun onFavorite(wine: Wine) {
        wine.isFavorite = !wine.isFavorite
        if (wine.isFavorite) vm.addWine(wine) else vm.deleteWine(wine)
    }

    override fun onLongClick(wine: Wine) {
        val fragmentManager = childFragmentManager
        val fragment = UpdateDialogFragment()
        val args = Bundle()
        args.putDouble(Constants.ARG_ID, wine.id)
        fragment.arguments = args
        fragment.show(fragmentManager, UpdateDialogFragment::class.java.simpleName)
        fragment.setOnUpdateListener {
            binding.srlResults.isRefreshing = true
            binding.viewModel?.getAllWines()
        }
    }
}