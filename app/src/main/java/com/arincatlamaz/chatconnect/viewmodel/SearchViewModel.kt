package com.arincatlamaz.chatconnect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arincatlamaz.chatconnect.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<User>>()
    val searchResults: LiveData<List<User>> get() = _searchResults

    private val databaseRef = FirebaseDatabase.getInstance().reference.child("users")
    private var currentSearchQuery: String = ""

    fun searchUsers(searchText: String) {
        currentSearchQuery = searchText
        fetchUsers()
    }

    private fun fetchUsers() {
        databaseRef.orderByChild("username")
            .startAt(currentSearchQuery)
            .endAt(currentSearchQuery + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val users = mutableListOf<User>()
                    for (snapshot in dataSnapshot.children) {
                        val username = snapshot.child("username").value.toString()
                        val user = User(username)
                        users.add(user)
                    }
                    _searchResults.value = users
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("DBERROR", error.message)
                }
            })
    }

    /*fun displaySearchResults(users: List<User>) {
        val resultText = StringBuilder()
        for (user in users) {
            resultText.append(user.username).append("\n")
        }
        Log.d("result:",resultText.toString())
    }*/
}