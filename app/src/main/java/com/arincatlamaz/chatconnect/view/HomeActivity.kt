package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewHome) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.messageDetailFragment){
                val layoutParams = ViewGroup.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT)
                val view = binding.root.getViewById(R.id.fragmentContainerViewHome)
                view.layoutParams = layoutParams
                binding.bottomNavigationView.visibility = View.GONE

            } else{
                binding.bottomNavigationView.visibility = View.VISIBLE

            }
        }

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
