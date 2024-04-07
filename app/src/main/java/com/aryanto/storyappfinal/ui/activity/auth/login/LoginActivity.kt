package com.aryanto.storyappfinal.ui.activity.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.core.data.model.LoginResult
import com.aryanto.storyappfinal.core.data.network.ApiClient
import com.aryanto.storyappfinal.databinding.ActivityLoginBinding
import com.aryanto.storyappfinal.ui.activity.auth.register.RegisterActivity
import com.aryanto.storyappfinal.ui.activity.home.HomeActivity
import com.aryanto.storyappfinal.utils.ClientState
import com.aryanto.storyappfinal.utils.TokenManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginVM: LoginVM by viewModel<LoginVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setView()
        setPageRegister()
        setBtnLogin()
        checkSession()
        applyAnimation()

    }

    override fun onResume() {
        super.onResume()
        checkSession()
    }

    private fun checkSession() {
        lifecycleScope.launch {
            val session = TokenManager.getInstance(this@LoginActivity).getSession()
            if (session) {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                return@launch
            }
        }
    }

    private suspend fun setTokenSession(token: String, isLoggedIn: Boolean) {
        TokenManager.getInstance(this@LoginActivity).saveTokenSession(token, isLoggedIn)
        ApiClient.setAuthToken(token)
    }

    private fun setView() {
        binding.apply {
            loginVM.login.observe(this@LoginActivity) { resources ->
                when (resources) {
                    is ClientState.SUCCESS -> {
                        progressBarLogin.visibility = View.GONE
                        resources.data?.let { handleLoginSuccess(it) }
                    }

                    is ClientState.ERROR -> {
                        progressBarLogin.visibility = View.GONE
                        handleError(resources.message)
                    }

                    is ClientState.LOADING -> {
                        progressBarLogin.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleLoginSuccess(loginResult: LoginResult) {
        val auth = loginResult.token

        lifecycleScope.launch {
            setTokenSession(auth, true)
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

    }

    private fun handleError(errorMSG: String?) {
        binding.apply {
            when {
                errorMSG?.contains("email") == true -> {
                    emailEdtLogin.setEmailError(errorMSG)
                }

                errorMSG?.contains("password") == true -> {
                    passwordEdtLogin.setPassError(errorMSG)
                }

                else -> {
                    showToast("$errorMSG")
                }
            }
        }
    }

    private fun setBtnLogin() {
        binding.apply {
            btnSubmitLogin.setOnClickListener {
                val email = emailEdtLogin.getEmail()
                val pass = passwordEdtLogin.getPass()
                loginVM.performLogin(email, pass)
            }
        }
    }

    private fun setPageRegister() {
        binding.apply {
            tvRegisterHere.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun applyAnimation() {
        binding.apply {
            val imgView = ivLogin
            val tv1 = tv1Login
            val tv2 = tv2Login
            val email = emailEdtLogin
            val pass = passwordEdtLogin
            val btnLogin = btnSubmitLogin
            val tv3 = tv3Login
            val tvRegister = tvRegisterHere

            val anim1 = ObjectAnimator.ofFloat(imgView, View.TRANSLATION_Y, -600f, 0f)
            val anim2 = ObjectAnimator.ofFloat(tv1, View.TRANSLATION_Y, -600f, 0f)
            val anim3 = ObjectAnimator.ofFloat(tv2, View.TRANSLATION_Y, -600f, 0f)
            val anim4 = ObjectAnimator.ofFloat(email, View.TRANSLATION_Y, -600f, 0f)
            val anim5 = ObjectAnimator.ofFloat(pass, View.TRANSLATION_Y, -600f, 0f)
            val anim6 = ObjectAnimator.ofFloat(btnLogin, View.TRANSLATION_Y, -600f, 0f)
            val anim7 = ObjectAnimator.ofFloat(tv3, View.TRANSLATION_Y, -600f, 0f)
            val anim8 = ObjectAnimator.ofFloat(tvRegister, View.TRANSLATION_Y, -600f, 0f)

            val duration = 3000L
            val interpolator = DecelerateInterpolator()

            anim1.duration = duration
            anim1.interpolator = interpolator

            anim2.duration = duration
            anim2.interpolator = interpolator

            anim3.duration = duration
            anim3.interpolator = interpolator

            anim4.duration = duration
            anim4.interpolator = interpolator

            anim5.duration = duration
            anim5.interpolator = interpolator

            anim6.duration = duration
            anim6.interpolator = interpolator

            anim7.duration = duration
            anim7.interpolator = interpolator

            anim8.duration = duration
            anim8.interpolator = interpolator

            val set = AnimatorSet()
            set.playTogether(
                anim1, anim2, anim3, anim4, anim5, anim6, anim7, anim8
            )
            set.start()

        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}