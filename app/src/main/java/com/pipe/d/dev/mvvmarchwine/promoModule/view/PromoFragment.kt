package com.pipe.d.dev.mvvmarchwine.promoModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pipe.d.dev.mvvmarchwine.BR
import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.FakeFirebaseAuth
import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.getAllPromos
import com.pipe.d.dev.mvvmarchwine.databinding.FragmentPromoBinding
import com.pipe.d.dev.mvvmarchwine.loginModule.model.LoginRepository
import com.pipe.d.dev.mvvmarchwine.loginModule.viewModel.LoginViewModel
import com.pipe.d.dev.mvvmarchwine.loginModule.viewModel.LoginViewModelFactory
import com.pipe.d.dev.mvvmarchwine.promoModule.model.Database
import com.pipe.d.dev.mvvmarchwine.promoModule.model.PromoRepository
import com.pipe.d.dev.mvvmarchwine.promoModule.viewModel.PromoViewModel
import com.pipe.d.dev.mvvmarchwine.promoModule.viewModel.PromoViewModelFactory
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
class PromoFragment : Fragment() {
    private var _binding: FragmentPromoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PromoListAdapter
    private lateinit var vm: PromoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentPromoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupAdapter()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this, PromoViewModelFactory(
            PromoRepository(Database())))[PromoViewModel::class.java]
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let { vm ->
            vm.snackBarMsg.observe(viewLifecycleOwner) { resMsg ->
                showMsg(resMsg)
            }
            vm.promos.observe(viewLifecycleOwner) {promos ->
                adapter.submitList(promos)
            }
        }
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupAdapter() {
        adapter = PromoListAdapter()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@PromoFragment.adapter
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}