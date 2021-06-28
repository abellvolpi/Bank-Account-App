package com.example.bankaccountapp.contas

import java.util.*

class Account {
    var number : Int
    var named : String
    var date : Date

    constructor(number: Int, named: String, date: Date) {
        this.number = number
        this.named = named
        this.date = date
    }
}