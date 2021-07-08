package com.example.bankaccountapp

import java.util.*

class Historic {

    val id1 : String
    val operacao : String
    val id2:  String
    val valor : String
    val data : String

    constructor(id1: String, operacao: String ,id2: String, valor: String, data: String) {
        this.id1 = id1
        this.id2 = id2
        this.valor = valor
        this.data = data
        this.operacao = operacao
    }
}