package com.arincatlamaz.chatconnect.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.arincatlamaz.chatconnect.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : BaseVM(application) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val _currentUser = MutableLiveData<FirebaseUser?>()

    fun signUp(email: EditText, password: EditText, username: EditText, context: Context, navController: NavController) {

        launch {
            if (username.text.isEmpty() || email.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_LONG).show()
                return@launch
            }

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users")
            val queryRef = usersRef.orderByChild("email").equalTo(email.text.toString())

            queryRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) { Toast.makeText(context, "E-mail is already registered!", Toast.LENGTH_LONG).show()
                        email.text.clear()
                    } else {
                        val usernameRef = usersRef.orderByChild("username").equalTo(username.text.toString())
                        usernameRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    Toast.makeText(context, "Username is already taken!", Toast.LENGTH_LONG).show()
                                    username.text.clear()
                                } else {
                                    firebaseAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                                        .addOnSuccessListener { authResult ->
                                            val profileUpdates = userProfileChangeRequest {
                                                displayName = username.text.toString()
                                            }
                                            authResult.user?.updateProfile(profileUpdates)

                                            val newUserRef = usersRef.child(authResult.user?.uid ?: "")
                                            newUserRef.child("username").setValue(username.text.toString())
                                            newUserRef.child("email").setValue(email.text.toString())
                                            newUserRef.child("password").setValue(password.text.toString())
                                            _currentUser.value = authResult.user
                                            Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
                                            navController.navigate(R.id.loginFragment)
                                        }
                                        .addOnFailureListener { exception ->
                                            Toast.makeText(context, "Error occurred: ${exception.message}", Toast.LENGTH_LONG).show()
                                        }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(context, "Error occurred: ${error.message}", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error occurred: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

    fun signIn(email: EditText, password: EditText, context: Context, navController: NavController) {
        launch {
            try {
                if (email.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                    Toast.makeText(context, "Please fill in all fields!", Toast.LENGTH_LONG).show()
                    return@launch
                } else{
                    val authResult = firebaseAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                        .addOnSuccessListener{
                            Toast.makeText(context,"Login successful",Toast.LENGTH_LONG).show()
                            navController.navigate(R.id.homeActivity)
                        }
                        .addOnFailureListener{
                            Toast.makeText(context,"There is no user record corresponding to this identifier",Toast.LENGTH_LONG).show()
                            email.text.clear()
                            password.text.clear()
                        }.await()

                    _currentUser.value = authResult.user
                }

            } catch (e: FirebaseAuthException) {
                Log.d("EXCEPTIONFB",e.message.toString())
            }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _currentUser.value = null
    }
}