package com.arincatlamaz.chatconnect.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.util.splashDelay


class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        splashDelay(requireActivity(), this.requireContext(), this)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}