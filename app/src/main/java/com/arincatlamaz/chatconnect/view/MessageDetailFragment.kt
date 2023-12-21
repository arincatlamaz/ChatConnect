package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arincatlamaz.chatconnect.adapter.MessageDetailAdapter
import com.arincatlamaz.chatconnect.databinding.FragmentMessageDetailBinding
import com.arincatlamaz.chatconnect.viewmodel.MessageViewModel
import com.google.firebase.auth.FirebaseAuth

class MessageDetailFragment : Fragment() {

    private lateinit var messageViewModel: MessageViewModel
    private lateinit var binding: FragmentMessageDetailBinding
    private lateinit var adapter: MessageDetailAdapter
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessageDetailBinding.inflate(inflater, container, false)
        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        val userId = arguments?.let { MessageDetailFragmentArgs.fromBundle(it).userId }
        val str: String? = userId

        binding.sendButton.setOnClickListener {
            if (str != null) {
                messageViewModel.sendMessage(binding.editTextSendMessage, str)
            }
        }

        setupDetailRecyclerView(userId!!)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun setupDetailRecyclerView(recId: String) {
        adapter = MessageDetailAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerviewDetail.layoutManager = layoutManager
        binding.recyclerviewDetail.adapter = adapter

        messageViewModel.getChats(recId) { messagesList ->
            adapter.clearItems()
            messagesList.forEach { adapter.addItem(it) }
            binding.recyclerviewDetail.layoutManager?.scrollToPosition(adapter.itemCount - 1)
        }
    }


}
