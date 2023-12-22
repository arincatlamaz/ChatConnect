package com.arincatlamaz.chatconnect.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.FragmentSettingsBinding
import com.arincatlamaz.chatconnect.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logout.setOnClickListener {
            val authViewModel: AuthViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
            authViewModel.signOut()
            startActivity(Intent(context, MainActivity::class.java))

        }

        val currentUser = Firebase.auth.currentUser
        binding.user = currentUser
    }

}