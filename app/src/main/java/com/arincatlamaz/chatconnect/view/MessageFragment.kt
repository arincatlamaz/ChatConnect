package com.arincatlamaz.chatconnect.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arincatlamaz.chatconnect.adapter.UserAdapter
import com.arincatlamaz.chatconnect.databinding.FragmentMessageBinding
import com.arincatlamaz.chatconnect.viewmodel.SearchViewModel


class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        setupRecyclerView()
        setupSearchView()
        observeSearchResults()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.recyclerviewListUsers.adapter = adapter
        binding.recyclerviewListUsers.layoutManager = LinearLayoutManager(context)

        binding.searchView.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus){
                    hideKeyboard()
                    adapter.clear()
                }
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
        viewModel.searchResults.observe(requireActivity()) { users ->
            if (binding.searchView.text.isNotEmpty()) {
                adapter.setUsers(users)
            } else {
                adapter.clear()
            }
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
        binding.searchView.text.clear()
    }

}

