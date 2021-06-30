package com.example.bankaccountapp.contas

import java.util.*
import java.time.format.DateTimeFormatter

abstract class Account {

    val accountNumber: Int
    var password: String
    var ownersName: String
    val creationDate : Date
    var balance: Long


    constructor(accountNumber: Int, ownersName: String, password: String, creationDate: Date, balance: Long) {
        this.accountNumber = accountNumber
        this.ownersName = ownersName
        this.password = password
        this.creationDate = Calendar.getInstance().time
        this.balance = 0
    }

    open fun withdraw(value: Long): Long {
        balance -= value
        return balance
    }

    open fun deposit (value: Long): Long{
        balance += value
        return balance
    }


    fun linhaCsv(): String{
        return "${accountNumber};${password};${ownersName};${creationDate};${balance}"
    }


//    fun deposit(value: Long): Long {
//
//    }
//
//    fun withdraw(value: Long): Long {
//
//    }

}