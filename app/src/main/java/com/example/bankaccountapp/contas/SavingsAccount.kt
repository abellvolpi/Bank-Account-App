package com.example.bankaccountapp.contas

import java.util.*

class SavingsAccount(
    accountNumber: Int,
    ownersName: String,
    password: String,
    creationDate: Date,
    balance: Long = 0L,
) : Account(accountNumber, ownersName, password, creationDate, balance) {

    override fun withdraw(value: Long): Long {
//
        return balance

    }

    override fun deposit(value: Long): Long {
        return balance
    }

}

