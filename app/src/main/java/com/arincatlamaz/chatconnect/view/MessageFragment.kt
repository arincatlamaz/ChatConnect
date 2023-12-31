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
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arincatlamaz.chatconnect.adapter.UserAdapter
import com.arincatlamaz.chatconnect.databinding.FragmentMessageBinding
import com.arincatlamaz.chatconnect.viewmodel.SearchViewModel

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        setupRecyclerView()
        setupSearchView()
        observeSearchResults()

        backProcess(binding.searchView)

        return binding.root
    }

    private fun navigateToMessageDetailFragment(userId: String) {
        Log.d("MessageFragment", "Navigating to details with userId: $userId")
        val action = MessageFragmentDirections.actionMessageFragmentToMessageDetailFragment(userId)
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.recyclerviewListUsers.adapter = adapter
        binding.recyclerviewListUsers.layoutManager = LinearLayoutManager(context)
        adapter.setOnUserClickListener{username ->
            viewModel.findUserIdByUsername(username){userId: String ->
                navigateToMessageDetailFragment(userId)

            }
        }

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
                Log.d("beforeTextChanged", "$p0, $p1, $p2, $p3")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchUsers(p0.toString())
                Log.d("onTextChanged", "$p0, $p1, $p2, $p3")
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("afterTextChanged", "$p0")
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

/**
 * Clear search edittext while returning to fragment
 */
    private fun backProcess(searchView: EditText, savedStateHandleKey: String = "needToClearSearchView") {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(savedStateHandleKey)
            ?.observe(viewLifecycleOwner) { shouldClear ->
                if (shouldClear) {
                    searchView.text.clear()
                    navController.currentBackStackEntry?.savedStateHandle?.set(savedStateHandleKey, false)
                }
            }
    }

}

