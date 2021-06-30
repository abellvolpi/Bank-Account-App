package com.example.bankaccountapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.bankaccountapp.R

class NewAccount : AppCompatActivity() {


    private lateinit var nomeCompleto: EditText
    private lateinit var password: EditText
    private lateinit var buttonCreateAccount : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)




    }

    private fun adicionarConta(){

    }


}