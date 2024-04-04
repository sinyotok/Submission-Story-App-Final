package com.aryanto.storyappfinal.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.databinding.ItemBinding
import com.aryanto.storyappfinal.ui.activity.detail.DetailActivity
import com.bumptech.glide.Glide

class PagingAdapter : PagingDataAdapter<Story, PagingAdapter.HomeViewHolder>(myDiffCB) {
    class HomeViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Story){
            binding.apply {
                nameItem.text = user.name
                descriptionItem.text = user.description
                createdAt.text = user.createdAt

                Glide.with(root)
                    .load(user.photoUrl)
                    .into(imageItem)

                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java)
                    intent.putExtra("user", user)

                    val optionCompact: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            root.context as Activity,
                            Pair(nameItem, "shared_name"),
                            Pair(imageItem, "shared_image"),
                            Pair(createdAt, "shared_created"),
                            Pair(descriptionItem, "shared_desc"),
                        )

                    root.context.startActivity(intent, optionCompact.toBundle())

                }

            }
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position).let {
            if (it != null) {
                holder.bind(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    companion object {
        val myDiffCB = object : DiffUtil.ItemCallback<Story>() {

            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

        }

    }

}