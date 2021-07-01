package com.example.bankaccountapp.utils

import android.content.Context
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.contas.CurrentAccount
import com.example.bankaccountapp.contas.SavingsAccount
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

object Csv {

    var contas: ArrayList<Account>? = null



    fun lerCsv(context: Context): ArrayList<Account> {

        return contas ?: arrayListOf<Account>().also { list ->

            val file = File(context.cacheDir, "accounts.csv")
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            var row: List<String>

            while (bufferedReader.ready()) {

                row = bufferedReader.readLine().split(';')

                when {
                    row[1] == "Savings Account" ->
                        list.add(
                            SavingsAccount(
                                row[0].toInt(),
                                row[2],
                                row[3],
                                convertToDate(row[4].toLong()),
                                row[5].toLong()
                            )
                        )
                    row[1] == "Current Account" ->
                        list.add(
                            CurrentAccount(
                                row[0].toInt(),
                                row[2],
                                row[3],
                                convertToDate(row[4].toLong()),
                                row[5].toLong()
                            )
                        )
                    else -> null
                }
            }
            contas = list
        }
    }
    fun convertToDate(time: Long): Date{
        return Date(time)
    }

}