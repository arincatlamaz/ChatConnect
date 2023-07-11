package com.arincatlamaz.chatconnect.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.view.HomeActivity
import com.arincatlamaz.chatconnect.view.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var auth: FirebaseAuth

fun splashDelay(activity: FragmentActivity, context: Context, fragment: Fragment?){

    val delay = 2000L
    Handler(Looper.getMainLooper()).postDelayed({
        fragment?.findNavController()?.navigate(R.id.splashToOnBoarding)
    }, delay)
    auth = Firebase.auth
    val currentUser = auth.currentUser

    if (currentUser != null){
        context.startActivity(Intent(context, HomeActivity::class.java))
    }



}














