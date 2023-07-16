package com.arincatlamaz.chatconnect.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.databinding.FragmentMessageDetailBinding

class MessageDetailFragment : Fragment() {

    private lateinit var binding: FragmentMessageDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMessageDetailBinding.inflate(inflater, container, false)

        binding.editTextSendMessage.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.editTextSendMessage.postDelayed({
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(binding.editTextSendMessage, InputMethodManager.SHOW_IMPLICIT)
                }, 100)
            }
        }


        return inflater.inflate(R.layout.fragment_message_detail, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


}
