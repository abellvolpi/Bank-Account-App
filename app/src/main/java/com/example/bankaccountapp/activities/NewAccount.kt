package com.example.bankaccountapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import com.example.bankaccountapp.R
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class NewAccount : AppCompatActivity() {


    private lateinit var nomeCompleto: EditText
    private lateinit var password: EditText
    private lateinit var buttonCreateAccount : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        nomeCompleto = findViewById(R.id.create_account_nomecompleto)
        password = findViewById(R.id.create_account_senha)
        buttonCreateAccount = findViewById(R.id.create_account_button)
        var count = 0


        buttonCreateAccount.setOnClickListener{
            if(nomeCompleto.text.isEmpty()){

            }
            else{
                adicionarConta()
                count++
            }

        }

    }

    private fun adicionarConta(nome : String, senha: String){
        var saldo = 0
        var formataData: SimpleDateFormat = ("dd-MM-yyyy")
        var data: Date = Date()
        var dataFormatada: String = formataData.format(data)
        val file = File(cacheDir, "accounts.csv")
        val fileWriter = FileWriter(file, true)
        fileWriter.append("${count};${nome};${senha};${dataFormatada};${saldo}")
        fileWriter.close()
        count++
    }



}