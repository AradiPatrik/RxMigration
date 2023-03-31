package com.aradipatrik.rxmigration.ui.home

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aradipatrik.rxmigration.databinding.ListItemRepoBinding
import com.aradipatrik.rxmigration.domain.Repo
import com.google.android.material.R.attr
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object RepoDiffCallback : DiffUtil.ItemCallback<RepoListItem>() {
    override fun areItemsTheSame(oldItem: RepoListItem, newItem: RepoListItem) =
        oldItem.item.id == newItem.item.id

    override fun areContentsTheSame(oldItem: RepoListItem, newItem: RepoListItem) =
        oldItem == newItem
}

class RepoAdapter : ListAdapter<RepoListItem, RepoAdapter.ViewHolder>(RepoDiffCallback) {

    private val _itemClicks = MutableSharedFlow<RepoListItem>(extraBufferCapacity = 1)
    val itemClicks = _itemClicks.asSharedFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(inflateView(parent), onClick = { _itemClicks.tryEmit(it) })

    private fun inflateView(parent: ViewGroup) =
        ListItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemRepoBinding,
        private val onClick: (RepoListItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepoListItem) {
            binding.nameTv.text = item.item.name
            binding.descriptionTv.text = item.item.description
            binding.profileIv.load(item.item.ownerAvatarUrl)
            binding.starTv.text = item.item.stars.toString()
            binding.forkTv.text = item.item.forks.toString()
            binding.cardView.setOnClickListener { onClick(item) }
            binding.starIcon.imageTintList = ColorStateList.valueOf(
                if (item.isSelected) {
                    binding.root.context.getColor(android.R.color.holo_orange_dark)
                } else {
                    getThemeAttributeColor(binding.root.context, attr.colorOnSurface)
                }
            )
        }

        private fun getThemeAttributeColor(context: Context, @AttrRes attribute: Int): Int {
            val typedValue = TypedValue()
            val theme = context.theme
            theme.resolveAttribute(attribute, typedValue, true)
            return typedValue.data
        }
    }
}