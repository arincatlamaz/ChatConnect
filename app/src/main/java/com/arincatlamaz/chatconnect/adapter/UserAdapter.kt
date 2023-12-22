package com.arincatlamaz.chatconnect.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arincatlamaz.chatconnect.databinding.UserListItemBinding
import com.arincatlamaz.chatconnect.model.User


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val users: MutableList<User> = mutableListOf()
    private var onUserClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    fun setOnUserClickListener(listener: (String) -> Unit) {
        onUserClickListener = listener
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)

        Log.d("Clicked Item:",itemCount.toString())

        holder.itemView.setOnClickListener {
            onUserClickListener?.invoke(user.username)
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUsers(userList: List<User>) {
        users.clear()
        users.addAll(userList)
        notifyDataSetChanged()
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.listItemUsername.text = user.username
        }
    }
}
