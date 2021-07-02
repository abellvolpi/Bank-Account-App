package com.example.bankaccountapp.contas

import java.io.Serializable
import java.util.*
import java.time.format.DateTimeFormatter

abstract class Account : Serializable {

    val accountNumber: Int
    var password: String
    var ownersName: String
    val creationDate: Date
    var balance: Long


    constructor(
        accountNumber: Int,
        ownersName: String,
        password: String,
        creationDate: Date,
        balance: Long
    ) {
        this.accountNumber = accountNumber
        this.ownersName = ownersName
        this.password = password
        this.creationDate = creationDate
        this.balance = balance
    }

    open fun withdraw(value: Long): Long {
        balance -= value
        return balance
    }

    open fun deposit(value: Long): Long {
        balance += value
        return balance
    }
}

