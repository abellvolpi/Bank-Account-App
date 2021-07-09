package com.example.bankaccountapp.models

import java.util.*

class CurrentAccount(
    accountNumber: Int,
    ownersName: String,
    password: String,
    creationDate: Date,
    balance: Long = 0L,
) : Account(accountNumber, ownersName, password, creationDate, balance) {


    override fun withdraw(value: Long): Long {

        return balance

    }

}