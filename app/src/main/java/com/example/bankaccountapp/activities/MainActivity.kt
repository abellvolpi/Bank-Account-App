package com.example.bankaccountapp.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.contas.CurrentAccount
import com.example.bankaccountapp.contas.SavingsAccount
import com.example.bankaccountapp.utils.Csv
import java.io.File
import java.io.FileWriter
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var contas: ArrayList<Account>? = null
        var contacorrente = CurrentAccount(19, "joao", "senha", Calendar.getInstance().time, 0)
        contas?.add(contacorrente)
        println(contacorrente)

        fun criarCsv(contas: ArrayList<Account>) {
            val file = File(cacheDir, "accounts.csv")
            val printWriter = file.printWriter()
            contas?.forEach { it ->
                printWriter.println(it.linhaCsv())
            }
            printWriter.flush()
        }

        if (contas != null) {
            criarCsv(contas)
        }


    }
}

