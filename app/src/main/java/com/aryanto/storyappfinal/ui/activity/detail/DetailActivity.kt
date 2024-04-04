package com.aryanto.storyappfinal.ui.activity.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.core.data.model.Story
import com.aryanto.storyappfinal.databinding.ActivityDetailBinding
import com.aryanto.storyappfinal.utils.ClientState
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var storyId: String

    private val detailVM: DetailVM by viewModel<DetailVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val story: Story? = intent.getParcelableExtra("user")
        if (story != null) {
            storyId = story.id
            handleDetail(story)
        }

        detailVM.detail(storyId)

        setView()

        applyTransition()

    }

    private fun setView() {
        binding.apply {
            detailVM.detail.observe(this@DetailActivity) { resources ->
                when (resources) {
                    is ClientState.SUCCESS -> {
                        detailProgressBar.visibility = View.GONE
                        resources.data?.let { handleDetail(it) }
                    }

                    is ClientState.ERROR -> {
                        detailProgressBar.visibility = View.GONE
                        showToast("${resources.message}")
                    }

                    is ClientState.LOADING -> {
                        detailProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleDetail(detailView: Story) {
        binding.apply {
            detailName.text = detailView.name
            detailCreatedAt.text = detailView.createdAt
            detailDescription.text = detailView.description

            Glide.with(this@DetailActivity)
                .load(detailView.photoUrl)
                .into(detailImage)

        }
    }

    private fun applyTransition() {
        binding.apply {
            val sharedImage = detailImage
            val sharedName = detailName
            val sharedCreated = detailCreatedAt
            val sharedDesc = detailDescription

            ViewCompat.setTransitionName(sharedImage, "shared_image")
            ViewCompat.setTransitionName(sharedName, "shared_name")
            ViewCompat.setTransitionName(sharedCreated, "shared_created")
            ViewCompat.setTransitionName(sharedDesc, "shared_desc")

            val transition = TransitionInflater.from(this@DetailActivity)
                .inflateTransition(R.transition.shared_element)
            window.sharedElementEnterTransition = transition
            window.sharedElementExitTransition = transition

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DetailActivity, message, Toast.LENGTH_LONG).show()
    }

}