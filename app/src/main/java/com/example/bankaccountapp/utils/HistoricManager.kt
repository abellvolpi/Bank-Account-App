package com.example.bankaccountapp.utils

import com.example.bankaccountapp.models.Historic
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

object HistoricManager {


    fun lerCsvHistorico(id: Int): ArrayList<Historic>? {


        val file = File(MainApplication.applicationContext().cacheDir, "${id}.csv")
        if (file.exists()) {
            val fileReader = FileReader(file)
            val bufferedReader = BufferedReader(fileReader)
            var row: List<String>
            val list: ArrayList<Historic> = arrayListOf()
            while (bufferedReader.ready()) {

                row = bufferedReader.readLine().split(';')
                list.add(
                    Historic(
                        row[0],
                        row[1],
                        row[2],
                        row[3],
                        row[4]
                    )
                )
            }
            return list
        } else {
            return null
        }
    }


    fun destino(id: Int): String {
        AccountManager.lerCsv().forEach {
            if (it.accountNumber == id) {
                return it.ownersName
            }
        }
        return ""
    }


}