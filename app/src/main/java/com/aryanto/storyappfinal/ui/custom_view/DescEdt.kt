package com.aryanto.storyappfinal.ui.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.LinearLayout
import com.aryanto.storyappfinal.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class DescEdt : LinearLayout {
    private lateinit var tiLayout: TextInputLayout
    private lateinit var editText: TextInputEditText
    private var isValidationError = false

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
        inflate(context, R.layout.custom_desc_edt, this)
        tiLayout = findViewById(R.id.custom_desc_ti_layout)
        editText = findViewById(R.id.custom_desc_edt)

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                clearError()
            } else {
                clearError()
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearError()
            }

            override fun afterTextChanged(s: Editable?) {
                clearError()
            }

        })

    }


    fun getDesc(): String {
        return editText.text.toString().trim()
    }

    fun setDescError(errorMSG: String?) {
        tiLayout.error = errorMSG
        isValidationError = true
    }

    fun clearError() {
        tiLayout.error = null
        tiLayout.isErrorEnabled = false
        isValidationError = false
    }

}