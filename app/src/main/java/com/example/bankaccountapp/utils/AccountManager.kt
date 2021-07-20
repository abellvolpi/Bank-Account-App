package com.example.bankaccountapp.utils

import com.example.bankaccountapp.activities.NewAccountFragment
import com.example.bankaccountapp.models.Account
import com.example.bankaccountapp.models.CurrentAccount
import com.example.bankaccountapp.models.SavingsAccount
import java.io.*

import java.util.*
import kotlin.collections.ArrayList

object AccountManager {

    private var contas: ArrayList<Account>? = null

    fun adicionarConta(conta: Account) {
        val contas = lerCsv()
        contas.add(conta)
        val file = File(MainApplication.applicationContext().cacheDir, "accounts.csv")
        val fileWriter = FileWriter(file, true)
        val type = if (conta is CurrentAccount) {
            "Current Account"
        } else {
            "Savings Account"
        }
        fileWriter.append("${conta.accountNumber};${type};${conta.ownersName};${conta.password};${conta.creationDate.time};${conta.balance}\n")
        fileWriter.close()
    }

    fun lerCsv(): ArrayList<Account> {

        return contas ?: arrayListOf<Account>().also { list ->

            val file = File(MainApplication.applicationContext().cacheDir, "accounts.csv")
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
                }
            }
            contas = list
        }
    }

    fun convertToDate(time: Long): Date {
        return Date(time)
    }


    fun escreverCsv() {
        val contas = lerCsv()
        val file = File(MainApplication.applicationContext().cacheDir, "accounts.csv")
        val fileWriter = FileWriter(
            file,
            false
        ) // true ele adiciona a informação de agora no arquivo já existente, e false ele ignora o que tinha antes e reescreve do 0
        contas.forEach {
            if (it is CurrentAccount) {
                fileWriter.append("${it.accountNumber};Current Account;${it.ownersName};${it.password};${it.creationDate.time};${it.balance}\n")
            } else {
                fileWriter.append("${it.accountNumber};Savings Account;${it.ownersName};${it.password};${it.creationDate.time};${it.balance}\n")
            }
        }
        fileWriter.close()
    }

    //contaCorrente é true, contaPoupança false
    fun existeContaCorrente(nome: String, isCurrentAccount: Boolean): Boolean {
        lerCsv().forEach {
            if (it.ownersName == nome && it is CurrentAccount == isCurrentAccount) {
                return true
            }
        }
        return false
    }

    fun accountGenerator() {


        for (i in 1..10000) {

            if (i % 2 == 0) {
                adicionarConta(CurrentAccount(newAccountNumber(),"Nome${i}", i.toString().toSHA256(),Calendar.getInstance().time))
            } else {
                adicionarConta(SavingsAccount(newAccountNumber(),"Nome${i}",i.toString().toSHA256(),Calendar.getInstance().time))
            }
        }
    }


    fun newAccountNumber(): Int {
        var maior = 0
        val contas = AccountManager.lerCsv()
        contas.forEach {
            if (it.accountNumber > maior) {
                maior = it.accountNumber
            }
        }
        return maior + 1
    }


}

