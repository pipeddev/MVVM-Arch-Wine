package com.pipe.d.dev.mvvmarchwine.accountModule

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.pipe.d.dev.mvvmarchwine.BR
import com.pipe.d.dev.mvvmarchwine.common.utils.Constants
import com.pipe.d.dev.mvvmarchwine.common.dataAccess.local.FakeFirebaseAuth
import com.pipe.d.dev.mvvmarchwine.mainModule.MainActivity
import com.pipe.d.dev.mvvmarchwine.R
import com.pipe.d.dev.mvvmarchwine.accountModule.model.AccountRepository
import com.pipe.d.dev.mvvmarchwine.accountModule.viewModel.AccountViewModel
import com.pipe.d.dev.mvvmarchwine.accountModule.viewModel.AccountViewModelFactory
import com.pipe.d.dev.mvvmarchwine.databinding.FragmentAccountBinding
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
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupIntents()
        setupObservers()
    }

    private fun setupObservers() {
        binding.viewModel?.let {vm ->
            vm.snackBarMsg.observe(viewLifecycleOwner) {resMsg ->
                showMsg(resMsg)
            }
            vm.isSignOut.observe(viewLifecycleOwner) {isSignOut ->
                if (isSignOut) {
                    (requireActivity() as MainActivity).apply {
                        setupNavView(false)
                        launchLoginUI()
                    }
                }
            }
        }
    }

    private fun setupViewModel() {
        vm = ViewModelProvider(this,
            AccountViewModelFactory(AccountRepository(FakeFirebaseAuth()))
        )[AccountViewModel::class.java]
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupIntents() {
        binding.tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(Constants.DATA_MAIL_TO)
                putExtra(Intent.EXTRA_EMAIL, arrayOf(binding.tvEmail.text.toString()))
                putExtra(Intent.EXTRA_SUBJECT, "From kotlin architectures course")
            }
            launchIntent(intent)
        }

        binding.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                val phone = (it as TextView).text
                data = Uri.parse("${Constants.DATA_TEL}$phone")
            }
            launchIntent(intent)
        }
    }

    private fun launchIntent(intent: Intent){
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireActivity(), getString(R.string.account_error_no_resolve), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}