package com.arincatlamaz.chatconnect.viewmodel

import android.app.Application
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.arincatlamaz.chatconnect.adapter.MessageDetailAdapter
import com.arincatlamaz.chatconnect.model.Chat
import com.arincatlamaz.chatconnect.view.MessageDetailFragmentArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.launch
import java.util.UUID

class MessageViewModel(application: Application) : BaseVM(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val chatRef = database.getReference("ChatList")

    fun sendMessage(msg: EditText, receiverId: String){

        viewModelScope.launch {
            val uuid = UUID.randomUUID()
            chatRef.child("$uuid").child("usermessage").setValue("${msg.text}")
            chatRef.child("$uuid").child("sender").setValue(auth.currentUser?.uid)
            chatRef.child("$uuid").child("receiver").setValue(receiverId)
            chatRef.child("$uuid").child("usermessageTime").setValue(ServerValue.TIMESTAMP)

            msg.text.clear()

        }

    }

    fun getChats(adapter: MessageDetailAdapter, receiverId: String) {
        viewModelScope.launch {
            val query: Query = chatRef.orderByChild("receiver").equalTo(receiverId)
            query.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val messagesList = mutableListOf<Chat>()
                    adapter.clearItems()
                    for (ds: DataSnapshot in snapshot.children) {
                        val hashMap = ds.value as? HashMap<String, Any> ?: continue
                        val chat = Chat(
                            txtUsername = hashMap["sender"] as? String ?: "",
                            chatText = hashMap["usermessage"] as? String ?: "",
                            txtTime = hashMap["usermessageTime"] as? Long ?: 0L

                        )
                        messagesList.add(chat)
                    }

                    messagesList.sortBy { it.txtTime }
                    adapter.clearItems()
                    messagesList.forEach{adapter.addItem(it)}
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.d("error", error.message)
                }
            })
        }
    }

}