package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.FragmentSignUpBinding
import com.arincatlamaz.chatconnect.viewmodel.AuthViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }


        binding.signUpBtn.setOnClickListener {
            val authViewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
            authViewModel.signUp(
                binding.email,
                binding.password,
                binding.username,
                requireContext(), navController = findNavController()
            )



        }

        return binding.root
    }

}