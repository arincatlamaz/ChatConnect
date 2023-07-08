package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        binding.signUpBtn.setOnClickListener{
            findNavController().navigate(R.id.onBoardingToSignup)
        }

        binding.loginBtn.setOnClickListener{
            findNavController().navigate(R.id.onBoardingToLogin)
        }

        return binding.root
    }

}