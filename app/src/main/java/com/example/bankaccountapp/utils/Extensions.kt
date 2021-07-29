package com.example.bankaccountapp.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.security.MessageDigest
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toSHA256(): String {
    val messageDigest = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return messageDigest.fold("", { str, it -> str + "%02x".format(it) })
}

fun balanceFormated(long: Long): String {
    if (long < 99L) {
        return "0,${long}"
    }
    val formatter = DecimalFormat("###,###,##.##")
    return formatter.format(long).toString()
}

fun formatDateToHHMMYYYYHHMM(date: Date): String {
    val fmt = SimpleDateFormat("dd MMM")
    return fmt.format(date)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideSoftKeyboard() {
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}