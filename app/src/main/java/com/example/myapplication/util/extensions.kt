package com.example.myapplication.util

import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso

fun ImageView.load(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
}

fun Fragment.toast(@StringRes textResId: Int) =
    Toast.makeText(requireContext(), textResId, Toast.LENGTH_SHORT).show()

fun Fragment.disableUI(disable: Boolean) {
    if (disable)
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    else
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}