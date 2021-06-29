package com.example.bankaccountapp.contas

import java.util.*

class CurrentAccount(accountNumber: Int, ownersName: String, password: String) : Account(accountNumber, ownersName, password) {

    private var currency : Currency

    override fun withdraw(value: Long, currency: Currency): Long {

    }

//    open fun withdraw(value: Long): Long {
//        saldo -= value
//        return saldo
//    }


}