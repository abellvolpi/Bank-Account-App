package com.example.bankaccountapp.contas

import java.util.*

class SavingsAccount(accountNumber: Int, ownersName: String, password: String) :Account(accountNumber, ownersName, password) {

    override fun withdraw(value: Long): Long {
//        saldo -= value * juros
        return saldo

    }


}

