package com.pipe.d.dev.mvvmarchwine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.pipe.d.dev.mvvmarchwine.databinding.FragmentLoginBinding
import kotlinx.coroutines.delay
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
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAuth()
        setupButtons()
    }

    private fun checkAuth() {
        lifecycleScope.launch {
            showProgress(true)
            delay(2_500)
            val auth = FakeFirebaseAuth()
            if (auth.isValidAuth()) {
                closeLoginUI()
            } else {
                showForm(true)
            }
            showProgress(false)
        }
    }

    private fun setupButtons() {
        with(binding) {
            btnLogin.setOnClickListener {
                lifecycleScope.launch {
                    showProgress(true)
                    showForm(false)
                    val auth = FakeFirebaseAuth()
                    if (auth.login(etUsername.text.toString(), etPin.text.toString()))
                        closeLoginUI()
                    else {
                        showProgress(false)
                        showMsg(R.string.login_login_fail)
                        showForm(true)
                    }
                }
            }
        }
    }

    private fun showMsg(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    private fun showProgress(isVisible: Boolean) {
        binding.contentProgress.root.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showForm(isVisible: Boolean) {
        binding.contentLogin.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun closeLoginUI() {
        (requireActivity() as MainActivity).setupNavView(true)
        requireActivity().supportFragmentManager.beginTransaction().apply {
            remove(this@LoginFragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}