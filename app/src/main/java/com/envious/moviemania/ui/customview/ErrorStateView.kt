package com.envious.moviemania.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.envious.moviemania.R
import com.envious.moviemania.databinding.ErrorViewBinding

class ErrorStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val binding = ErrorViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setUpErrorView(
        title: String = resources.getString(R.string.error_state_title),
        message: String = resources.getString(R.string.error_state_message),
    ) {
        visibility = View.VISIBLE
        binding.textErrorTitle.text = title
        binding.textErrorMessage.text = message
    }
}
