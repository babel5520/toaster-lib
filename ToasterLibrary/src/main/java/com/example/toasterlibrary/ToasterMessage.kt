package com.example.toasterlibrary

import android.content.Context
import android.widget.Toast

object ToasterMessage {

    fun s(context: Context, message: String) {
        Toast.makeText(context, "Toast : $message", Toast.LENGTH_SHORT).show()
    }
}