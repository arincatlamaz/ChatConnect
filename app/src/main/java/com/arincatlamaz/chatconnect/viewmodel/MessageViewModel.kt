package com.arincatlamaz.chatconnect.viewmodel

import android.app.Application
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.viewModelScope
import com.arincatlamaz.chatconnect.adapter.MessageDetailAdapter
import com.arincatlamaz.chatconnect.model.Chat
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
    val database = FirebaseDatabase.getInstance().getReference("users").child("${auth.currentUser?.uid}")
    val dbchat = database.child("chat")

    fun sendMessage(msg: EditText){

        val uuid = UUID.randomUUID()
        viewModelScope.launch {
            database.child("chat").child("$uuid").child("usermessage").setValue("${msg.text}")
            database.child("chat").child("$uuid").child("username").setValue("${auth.currentUser?.displayName}")
            database.child("chat").child("$uuid").child("usermessageTime").setValue(ServerValue.TIMESTAMP)


            msg.text.clear()

        }

    }

    fun getData( adapter: MessageDetailAdapter){


        viewModelScope.launch {
            val query: Query = dbchat.orderByChild("usermessageTime")
            query.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    adapter.clearItems()

                    for (ds: DataSnapshot in snapshot.children){
                        Log.d("Keyolla:", "${ds.value}")
                        val hashMap = ds.value as? HashMap<String, String> ?: return

                        val getDataUsername = hashMap.get("username")
                        val getDataUserMessage = hashMap.get("usermessage")

                        val chatmsg = Chat("$getDataUsername : $getDataUserMessage")
                        adapter.addItem(chatmsg)

                        adapter.notifyDataSetChanged()


                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("error", error.message)
                }

            })
        }



    }




}