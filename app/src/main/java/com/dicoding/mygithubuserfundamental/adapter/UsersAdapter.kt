package com.dicoding.mygithubuserfundamental.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.databinding.ItemUsersBinding
import com.dicoding.mygithubuserfundamental.ui.UserDetailActivity

class UsersAdapter (private val users: List<ItemList>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (username, avatarUrl) = users[position]
        Glide.with(holder.itemView)
            .load(avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvUserName.text = username

        holder.itemView.setOnClickListener {
            val toDetail = Intent(holder.itemView.context, UserDetailActivity::class.java)
            toDetail.putExtra(USERNAME, users[position])
            holder.itemView.context.startActivity(toDetail)
        }
    }

    override fun getItemCount(): Int = users.size

    companion object {
        const val USERNAME = "username"
    }
}