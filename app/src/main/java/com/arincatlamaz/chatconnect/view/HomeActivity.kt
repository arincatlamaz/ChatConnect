package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.adapter.UserAdapter
import com.arincatlamaz.chatconnect.databinding.ActivityHomeBinding
import com.arincatlamaz.chatconnect.viewmodel.SearchViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()

    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.fragmentContainerViewHome)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.message -> navController.navigate(R.id.messageFragment)
                R.id.calls -> navController.navigate(R.id.callsFragment)
                R.id.settings -> navController.navigate(R.id.settingsFragment)
            }
            true
        }
    }


}
