package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.FragmentLoginBinding
import com.arincatlamaz.chatconnect.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.signUpBtn.setOnClickListener{
            findNavController().navigate(R.id.signUpFragment)
        }

        binding.loginBtn.setOnClickListener{
            val authViewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

            authViewModel.signIn(binding.email, binding.password, requireContext(),navController = findNavController())

        }


        return binding.root
    }

}