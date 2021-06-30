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

object Csv {

    var contas: ArrayList<Account>? = null


    private fun adicionarConta() {

    }


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
                        contas?.add(
                            SavingsAccount(
                                row[0].toInt(),
                                row[1],
                                row[2],
                                SimpleDateFormat("dd/MM/yyyy").parse(row[3]),
                                row[4].toLong()
                            )
                        )
                    row[1] == "Current Account" ->
                        contas?.add(
                            CurrentAccount(
                                row[0].toInt(),
                                row[1],
                                row[2],
                                SimpleDateFormat("dd/MM/yyyy").parse(row[3]),
                                row[4].toLong()
                            )
                        )
                    else -> null
                }

            }
        }
    }
//    fun criarCsv(context: Context): java.util.ArrayList<Account> {
//        return Csv.contas ?: arrayListOf<Account>().also { list ->
//            val file = File(context.cacheDir, "accounts.csv")
//            val printWriter = file.printWriter()
////                val sb = StringBuilder()
////                contas?.forEach {
////                    sb.append(it.accountNumber)
////                    sb.append(";")
////                    if (it is SavingsAccount) {
////                        sb.append("Savings Account")
////                    } else {
////                        sb.append("Current Account")
////                    }
////                    sb.append(";")
////                    sb.append(it.ownersName)
////                    sb.append(";")
////                    sb.append(it.password)
////                    sb.append(";")
////                    sb.append(it.creationDate)
////                    sb.append(";")
////                    sb.append(it.balance)
////                    sb.append("\n")
////                }
////                br.write(sb.toString())
////                br.close()
//            contas?.forEach { it ->
//                printWriter.println(it.linhaCsv())
//            }
//            printWriter.flush()
//        }
//
//
//    }
//}



}