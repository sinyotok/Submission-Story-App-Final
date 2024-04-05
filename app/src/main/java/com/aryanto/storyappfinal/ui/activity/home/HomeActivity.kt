package com.aryanto.storyappfinal.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.core.data.network.ApiClient
import com.aryanto.storyappfinal.databinding.ActivityHomeBinding
import com.aryanto.storyappfinal.ui.activity.auth.login.LoginActivity
import com.aryanto.storyappfinal.ui.activity.upload.UploadActivity
import com.aryanto.storyappfinal.utils.StateAdapter
import com.aryanto.storyappfinal.utils.TokenManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var pagingAdapter: PagingAdapter

    private val homeVM: HomeVM by viewModel<HomeVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.homeToolbar)

        setAdapter()

        setView()

    }

    private fun setAdapter() {
        binding.apply {
            pagingAdapter = PagingAdapter()
            homeListItem.layoutManager = LinearLayoutManager(this@HomeActivity)
            homeListItem.adapter = pagingAdapter.withLoadStateFooter(
                footer = StateAdapter { pagingAdapter.retry() }
            )
        }
    }

    private fun setView() {
        homeVM.story.observe(this) {
            lifecycleScope.launch {
                val tManager = TokenManager.getInstance(this@HomeActivity).getToken() ?: ""
                ApiClient.setAuthToken(tManager)
                pagingAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_upload -> {
                val intent = Intent(this, UploadActivity::class.java)
                startActivity(intent)

                true
            }

            R.id.action_map -> {
                val intent = Intent(this, UploadActivity::class.java)
                startActivity(intent)

                true
            }

            R.id.action_logout -> {
                lifecycleScope.launch {
                    val tokenManager = TokenManager.getInstance(this@HomeActivity)
                    tokenManager.clearTokenAndSession()
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

}