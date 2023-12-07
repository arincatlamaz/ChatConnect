package com.arincatlamaz.chatconnect.adapter

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arincatlamaz.chatconnect.databinding.ItemChatBinding
import com.arincatlamaz.chatconnect.model.Chat
import com.arincatlamaz.chatconnect.model.User

class MessageDetailAdapter : RecyclerView.Adapter<MessageDetailAdapter.DetailViewHolder>(){
    private val chats: MutableList<Chat> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageDetailAdapter.DetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(inflater, parent, false)


        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageDetailAdapter.DetailViewHolder, position: Int) {
        val chat = chats[position]
        holder.bind(chat)



    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun clearItems() {
        chats.clear()
        notifyDataSetChanged()
    }

    fun addItem(chat: Chat) {
        chats.add(chat)
        notifyItemInserted(chats.size - 1)
    }

    inner class DetailViewHolder(private val binding: ItemChatBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(chat: Chat){
                binding.rwItemTextView.text = chat.chatText
            }

    }
}