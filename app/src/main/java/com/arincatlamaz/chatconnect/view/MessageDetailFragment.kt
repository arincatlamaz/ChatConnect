package com.arincatlamaz.chatconnect.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.adapter.MessageDetailAdapter
import com.arincatlamaz.chatconnect.databinding.FragmentMessageDetailBinding
import com.arincatlamaz.chatconnect.model.Chat
import com.arincatlamaz.chatconnect.viewmodel.AuthViewModel
import com.arincatlamaz.chatconnect.viewmodel.MessageViewModel

class MessageDetailFragment : Fragment() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var binding: FragmentMessageDetailBinding
    private lateinit var adapter: MessageDetailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMessageDetailBinding.inflate(inflater, container, false)


        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)



        binding.sendButton.setOnClickListener{

            messageViewModel.sendMessage(binding.editTextSendMessage)


        }

//        setupDetailRecyclerView()

        val userId = arguments?.let { MessageDetailFragmentArgs.fromBundle(it).userId }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun setupDetailRecyclerView() {

        adapter = MessageDetailAdapter()
        binding.recyclerviewDetail.adapter = adapter
        binding.recyclerviewDetail.layoutManager = LinearLayoutManager(context)

        messageViewModel.getData(adapter)



    }




}
