package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.ActivityHomeBinding
import com.arincatlamaz.chatconnect.model.User
import com.arincatlamaz.chatconnect.viewmodel.SearchViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navController = findNavController(R.id.fragmentContainerViewHome)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.message -> {
                    navController.navigate(R.id.messageFragment)
                    true
                }

                R.id.calls -> {
                    navController.navigate(R.id.callsFragment)
                    true
                }

                R.id.settings -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }

                else -> false
            }
        }

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.search_list_item, R.id.list_username)

        binding.listview.adapter = adapter

        adapter.notifyDataSetChanged()

        binding.constraintLayout2.setOnClickListener {
            binding.searchView.clearFocus()
            adapter.clear()
        }

        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("beforeTextChanged","beforeTextChanged")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchUsers(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("afterTextChanged","afterTextChanged")
            }

        })

        viewModel.searchResults.observe(this) { users ->
            if (binding.searchView.text.isNotEmpty()){
                val usernames = users.map { it.username }
                adapter.clear()
                adapter.addAll(usernames)
            }else{
                adapter.clear()
            }


        }

    }



}

