package com.arincatlamaz.chatconnect.viewmodel

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arincatlamaz.chatconnect.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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
        val formattedQuery = currentSearchQuery.trim().toLowerCase()
        databaseRef.orderByChild("username")
            .startAt(formattedQuery)
            .endAt(formattedQuery + "\uf8ff")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val users = mutableListOf<User>()
                    for (snapshot in dataSnapshot.children) {
                        val username = snapshot.child("username").value.toString().toLowerCase()
                        if (username.startsWith(formattedQuery)) {
                            val user = User(username)
                            users.add(user)
                        }
                    }
                    _searchResults.value = users
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("DBERROR", error.message)
                }
            })
    }

    fun findUserIdByUsername(username: String, calback: (String) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")
        val query = usersRef.orderByChild("username").equalTo(username)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val uid = userSnapshot.key ?: return
                        println("Kullanıcı adı: $username, UID: $uid")
                        calback(uid)
                        return
                    }
                } else {
                    println("Kullanıcı adı '$username' ile eşleşen kullanıcı bulunamadı.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Veritabanı hatası: ${databaseError.message}")
            }
        })
    }

}