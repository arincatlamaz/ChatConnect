package com.arincatlamaz.chatconnect.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.FragmentSettingsBinding
import com.arincatlamaz.chatconnect.viewmodel.AuthViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.logout.setOnClickListener {
            val authViewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
            authViewModel.signOut()
            startActivity(Intent(context, MainActivity::class.java))

        }

        return binding.root
    }

}