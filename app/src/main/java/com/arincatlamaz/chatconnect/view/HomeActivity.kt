package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        setupViewModel()
        setupRecyclerView()
        setupSearchView()

        observeSearchResults()
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.recyclerviewListUsers.adapter = adapter
        binding.recyclerviewListUsers.layoutManager = LinearLayoutManager(this)
        binding.constraintLayout2.setOnClickListener {
            binding.searchView.clearFocus()
            adapter.clear()
        }
    }

    private fun setupSearchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("beforeTextChanged", "beforeTextChanged")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchUsers(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("afterTextChanged", "afterTextChanged")
            }
        })
    }

    private fun observeSearchResults() {
        viewModel.searchResults.observe(this) { users ->
            if (binding.searchView.text.isNotEmpty()) {
                adapter.setUsers(users)
            } else {
                adapter.clear()
            }
        }
    }
}
