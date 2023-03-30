package com.aradipatrik.rxmigration.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aradipatrik.rxmigration.databinding.ListItemRepoBinding
import com.aradipatrik.rxmigration.domain.Repo

object RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
}

class RepoAdapter : ListAdapter<Repo, RepoAdapter.ViewHolder>(RepoDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ListItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo) {
            binding.nameTv.text = item.name
            binding.descriptionTv.text = item.description
            binding.profileIv.load(item.ownerAvatarUrl)
            binding.starTv.text = item.stars.toString()
            binding.forkTv.text = item.forks.toString()
        }
    }
}