package com.example.bankaccountapp.utils

import java.security.MessageDigest
import android.app.Application
import android.content.Context
import java.text.DecimalFormat

fun String.toSHA256(): String {
    val messageDigest = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return messageDigest.fold("", { str, it -> str + "%02x".format(it) })
}

fun balanceFormated(long: Long): String{
    if(long<99L){
        return "0,${long}"
    }
    val formatter = DecimalFormat("###,###,##.##")
    return formatter.format(long).toString()
}