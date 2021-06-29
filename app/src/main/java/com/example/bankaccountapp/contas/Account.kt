package com.example.bankaccountapp.contas

import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class Account {

    var accountNumber: Int
    var password: String
    var ownersName: String
    var date: Date
    var saldo: Long


    constructor(accountNumber: Int, ownersName: String, password: String) {
        this.accountNumber = accountNumber
        this.ownersName = ownersName
        this.password = password
        this.date = Calendar.getInstance().time
        this.saldo = 0
    }



    open fun withdraw(value: Long): Long {
        saldo -= value
        return saldo
    }

    open fun deposit (value: Long): Long{
        saldo += value
        return saldo
    }


//    fun deposit(value: Long): Long {
//
//    }
//
//    fun withdraw(value: Long): Long {
//
//    }

}