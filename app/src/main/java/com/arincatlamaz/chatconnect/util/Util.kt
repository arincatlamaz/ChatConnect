package com.arincatlamaz.chatconnect.util

import android.content.Intent
import android.util.Log
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.arincatlamaz.chatconnect.R
import com.arincatlamaz.chatconnect.view.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private lateinit var auth: FirebaseAuth

/**
 * Displays a splash screen for 2 seconds, then navigates to the HomeActivity if a user is already
   logged in via Firebase Auth, or to an onboarding fragment if not logged in.
 */
fun splashDelay(activity: FragmentActivity, fragment: Fragment?) {
    CoroutineScope(Dispatchers.Main).launch {
        val delay = 2000L
        delay(delay)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
            activity.finish()
        } else {
            fragment?.findNavController()?.navigate(R.id.splashToOnBoarding)
        }
    }
}
















