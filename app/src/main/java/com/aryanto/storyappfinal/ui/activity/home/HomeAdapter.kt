package com.aryanto.storyappfinal.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.databinding.ItemBinding
import com.aryanto.storyappfinal.ui.activity.detail.DetailActivity
import com.bumptech.glide.Glide

class HomeAdapter(
    private var items: List<Story>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Story) {
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

                    root.context.startActivity(intent)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItem(newList: List<Story>) {
        val diffResult = DiffUtil.calculateDiff(myDiffCB(items, newList))
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        fun myDiffCB(oldList: List<Story>, newList: List<Story>) =
            object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldList.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition].id == newList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return oldList[oldItemPosition] == newList[newItemPosition]
                }

            }

    }

}