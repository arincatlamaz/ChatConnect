package com.arincatlamaz.chatconnect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.arincatlamaz.chatconnect.databinding.UserListItemBinding
import com.arincatlamaz.chatconnect.model.User
import com.arincatlamaz.chatconnect.view.MessageFragmentDirections


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)

        holder.itemView.setOnClickListener {
            val action = MessageFragmentDirections.actionMessageFragmentToMessageDetailFragment()
            holder.itemView.findNavController().navigate(action)
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
