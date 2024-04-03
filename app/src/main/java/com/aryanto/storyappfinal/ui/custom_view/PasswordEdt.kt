package com.aryanto.storyappfinal.ui.custom_view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.aryanto.storyappfinal.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class PasswordEdt : LinearLayout {
    private var defaultIconColor: Int = ContextCompat.getColor(context, R.color.default_icon_colorr)
    private var focusedIconColor: Int = ContextCompat.getColor(context, R.color.icon_focused_color)
    private var errorIconColor: Int = ContextCompat.getColor(context, R.color.icon_error_color)

    private lateinit var tiLayout: TextInputLayout
    private lateinit var editText: TextInputEditText
    private lateinit var icon: Drawable
    private var isValidationError = false
    private var validationPattern: String =
        "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}"

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        inflate(context, R.layout.custom_password_edt, this)
        tiLayout = findViewById(R.id.custom_password_ti_layout)
        editText = findViewById(R.id.custom_password_edt)

        val drawableIcon = editText.compoundDrawablesRelative
        icon = drawableIcon[0] ?: throw IllegalAccessException("Drawable not set")

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                updateIconColor()
            } else {
                updateIconColor()
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && s.toString().length < 8) {
                    tiLayout.error = "Password must be at least 8 characters"
                    isValidationError = true
                } else {
                    clearError()
                    validationPassword(s.toString())
                }
                updateIconColor()
            }

            override fun afterTextChanged(s: Editable?) {
                validationPassword(s.toString())
                updateIconColor()
            }

        })

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Reset icon color filter when the view is detached to avoid leaks
        icon.colorFilter = null
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Reapply color filter when the view is attached
        updateIconColor()
    }

    private fun validationPassword(password: String): Boolean {
        val pattern = Pattern.compile(validationPattern)
        return pattern.matcher(password).matches()
    }

    fun getPass(): String {
        return editText.text.toString().trim()
    }

    fun setPassError(errorMSG: String?) {
        tiLayout.error = errorMSG
        isValidationError = true
        updateIconColor()
    }

    fun clearError() {
        tiLayout.error = null
        tiLayout.isErrorEnabled = false
        isValidationError = false
        updateIconColor()
    }

    private fun updateIconColor() {
        if (isValidationError) {
            icon.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                errorIconColor,
                BlendModeCompat.SRC_ATOP
            )
        } else if (editText.isFocused) {
            icon.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                focusedIconColor,
                BlendModeCompat.SRC_ATOP
            )
        } else {
            icon.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                defaultIconColor,
                BlendModeCompat.SRC_ATOP
            )
        }
    }

}