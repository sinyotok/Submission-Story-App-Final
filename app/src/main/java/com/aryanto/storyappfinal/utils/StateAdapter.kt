package com.aryanto.storyappfinal.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.storyappfinal.databinding.LoadingBinding

class StateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<StateAdapter.StateViewHolder>() {
    class StateViewHolder(private val binding: LoadingBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                btnRetry.setOnClickListener { retry.invoke() }
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                if (loadState is LoadState.Error) {
                    msgErrorItem.text = loadState.error.localizedMessage
                }
                pBarItemLoading.isVisible = loadState is LoadState.Loading
                msgErrorItem.isVisible = loadState is LoadState.Error
                btnRetry.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: StateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): StateViewHolder {
        val binding = LoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StateViewHolder(binding, retry)
    }

}