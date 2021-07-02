package com.example.bankaccountapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account

const val STRING = "STRING"

class MenuActivity : AppCompatActivity() {

    private lateinit var buttonWithDraw : LinearLayout
    private lateinit var buttonDeposit : LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        buttonWithDraw = findViewById(R.id.buttonSacar)
        buttonDeposit = findViewById(R.id.buttondepositar)
        var account = intent.getSerializableExtra(ACCOUNT) as Account

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // result: ActivityResult -> só serve para definir o "nome" do it
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data?.extras?.getSerializable("novoSaldo") ?: ""  //?: se for nulo, realiza a string dps
                account = intent as Account
            }
        }
        // result: ActivityResult -> classe que vem com os dados da tela



        buttonDeposit.setOnClickListener{
            val intencao = Intent(this, ManageMoneyActivity::class.java).apply {
                putExtra(ACCOUNT,account)
                putExtra(STRING,"Digite o Valor do Depósito")
            }
            startForResult.launch(intencao)
        }

        buttonWithDraw.setOnClickListener{
            val intencao = Intent(this, ManageMoneyActivity::class.java).apply {
                putExtra(ACCOUNT,account)
                putExtra(STRING,"Deposite o valor do Saque")
            }
            startForResult.launch(intencao)        }
    }



}