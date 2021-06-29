package com.example.bankaccountapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.CurrentAccount
import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var contacorrente = CurrentAccount(19, "joao", "senha")
        println(contacorrente)
    }


}