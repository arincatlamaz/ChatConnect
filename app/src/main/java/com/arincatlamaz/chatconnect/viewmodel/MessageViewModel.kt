package com.arincatlamaz.chatconnect.viewmodel

import android.app.Application
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.arincatlamaz.chatconnect.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.util.UUID

class MessageViewModel(application: Application) : BaseVM(application) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val chatRef = database.getReference("ChatList")

    /*
     * Creating child in db and sending inserted info.
     */
    fun sendMessage(msg: EditText, receiverId: String) {

        viewModelScope.launch {
            val uuid = UUID.randomUUID()
            chatRef.child("$uuid").child("usermessage").setValue("${msg.text}")
            chatRef.child("$uuid").child("sender").setValue(auth.currentUser?.uid)
            chatRef.child("$uuid").child("receiver").setValue(receiverId)
            chatRef.child("$uuid").child("usermessageTime").setValue(ServerValue.TIMESTAMP)

            msg.text.clear()

        }

    }

    /*
     *Getting messages via filtering receiver and sender ID's
     */
    fun getChats(otherUserId: String, onMessagesReceived: (List<Chat>) -> Unit) {
        viewModelScope.launch {
            chatRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList = mutableListOf<Chat>()
                    for (ds: DataSnapshot in snapshot.children) {
                        val hashMap = ds.value as? HashMap<*, *> ?: continue
                        val senderId = hashMap["sender"] as? String ?: ""
                        val FBrecId = hashMap["receiver"] as? String ?: ""
                        Log.d("Testing Log:", "The asd asd asd")

                        if ((senderId == auth.currentUser?.uid.toString() && FBrecId == otherUserId) ||
                            (senderId == otherUserId && FBrecId == auth.currentUser?.uid.toString())
                        ) {
                            getUsernameForId(senderId) { username ->
                                val chat = Chat(
                                    txtUsername = username,
                                    chatText = hashMap["usermessage"] as? String ?: "",
                                    txtTime = hashMap["usermessageTime"] as? Long ?: 0L
                                )
                                Log.d("Testing Log:", "There is receiver id like 0000")

                                messagesList.add(chat)
                                messagesList.sortBy { it.txtTime }
                                onMessagesReceived(messagesList)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("error", error.message)
                }
            })
        }
    }

    /*
     * Finding which ID belongs to which username.
     */
    fun getUsernameForId(userId: String, onUsernameReceived: (String) -> Unit) {
        viewModelScope.launch {
            val userRef = database.getReference("users").child(userId)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val username = snapshot.child("username").getValue(String::class.java)
                    if (username != null) {
                        onUsernameReceived(username)
                    } else {
                        Log.e("getUsernameForId", "Username for $userId not found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("error", error.message)
                }

            })
        }
    }
}