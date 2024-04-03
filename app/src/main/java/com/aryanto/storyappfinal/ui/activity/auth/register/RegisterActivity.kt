package com.aryanto.storyappfinal.ui.activity.auth.register

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
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.databinding.ActivityRegisterBinding
import com.aryanto.storyappfinal.ui.activity.auth.login.LoginActivity
import com.aryanto.storyappfinal.utils.ClientState
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerVM: RegisterVM by viewModel<RegisterVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        applyAnimation()
        setPageLogin()
        setBtnRegister()
        setView()

    }

    private fun setView() {
        binding.apply {
            registerVM.register.observe(this@RegisterActivity) { resources ->
                when (resources) {
                    is ClientState.SUCCESS -> {
                        progressBarRegister.visibility = View.GONE
                        resources.data?.let { handleSuccess() }
                    }

                    is ClientState.ERROR -> {
                        progressBarRegister.visibility = View.GONE
                        handleError(resources.message)
                    }

                    is ClientState.LOADING -> {
                        progressBarRegister.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleSuccess() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleError(errorMSG: String?) {
        binding.apply {
            when {
                errorMSG?.contains("name") == true -> {
                    nameEdtRegister.setNameError(errorMSG)
                }

                errorMSG?.contains("email") == true -> {
                    emailEdtRegister.setEmailError(errorMSG)
                }

                errorMSG?.contains("password") == true -> {
                    passwordEdtRegister.setPassError(errorMSG)
                }

                else -> {
                    showToast("$errorMSG")
                }

            }
        }
    }

    private fun setBtnRegister() {
        binding.apply {
            btnSubmitRegister.setOnClickListener {
                val name = nameEdtRegister.getName()
                val email = emailEdtRegister.getEmail()
                val pass = passwordEdtRegister.getPass()
                registerVM.performRegister(name, email, pass)
            }
        }
    }

    private fun setPageLogin() {
        binding.apply {
            tvLoginHere.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun applyAnimation() {
        binding.apply {

            val imgView = ivRegister
            val tv1 = tv1Register
            val name = nameEdtRegister
            val email = emailEdtRegister
            val pass = passwordEdtRegister
            val btnRegister = btnSubmitRegister
            val tv2 = tv2Register
            val tvLogin = tvLoginHere

            val anim1 = ObjectAnimator.ofFloat(imgView, View.TRANSLATION_Y, -600f, 0f)
            val anim2 = ObjectAnimator.ofFloat(tv1, View.TRANSLATION_Y, -600f, 0f)
            val anim3 = ObjectAnimator.ofFloat(name, View.TRANSLATION_Y, -600f, 0f)
            val anim4 = ObjectAnimator.ofFloat(email, View.TRANSLATION_Y, -600f, 0f)
            val anim5 = ObjectAnimator.ofFloat(pass, View.TRANSLATION_Y, -600f, 0f)
            val anim6 = ObjectAnimator.ofFloat(btnRegister, View.TRANSLATION_Y, -600f, 0f)
            val anim7 = ObjectAnimator.ofFloat(tv2, View.TRANSLATION_Y, -600f, 0f)
            val anim8 = ObjectAnimator.ofFloat(tvLogin, View.TRANSLATION_Y, -600f, 0f)

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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}