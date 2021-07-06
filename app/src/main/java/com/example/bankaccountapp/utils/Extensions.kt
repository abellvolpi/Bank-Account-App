package com.example.bankaccountapp.utils

import android.accounts.Account
import java.security.MessageDigest
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import java.text.DecimalFormat

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

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    containerId: Int,
    tag: String = "",
    addToStack: Boolean = true,
    stackName: String? = null
) {
    supportFragmentManager.commit {
        replace(containerId, fragment, tag)
        if (addToStack) {
            supportFragmentManager.findFragmentById(containerId)?.let {
                addToBackStack(stackName)
            }
        }
    }
}

fun Fragment.replaceFragment(
    fragment: Fragment,
    containerId: Int,
    tag: String = "",
    addToStack: Boolean = true,
    stackName: String? = null
) {
    activity?.replaceFragment(
        fragment,
        containerId,
        tag,
        addToStack,
        stackName
    )
}


fun Fragment.popBackStack(stackName: String? = null, flags: Int = 0) {
    activity?.supportFragmentManager?.popBackStackImmediate(stackName, flags)
}