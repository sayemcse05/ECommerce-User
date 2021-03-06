package com.example.ecomuserbatch4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecomuserbatch4.databinding.FragmentLoginBinding
import com.example.ecomuserbatch4.viewmodels.LoginViewModel

class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        loginViewModel.authLD.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.Authentication.AUTHENTICATED) {
                findNavController().popBackStack()
            }
        }
        loginViewModel.errMsgLD.observe(viewLifecycleOwner) {
            binding.errMsgTV.text = it
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val pass = binding.passET.text.toString()
            // TODO: validate fields
            loginViewModel.loginUser(email, pass)

        }
        binding.registerBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val pass = binding.passET.text.toString()
            // TODO: validate fields
            loginViewModel.registerUser(email, pass)

        }
        return binding.root
    }

}