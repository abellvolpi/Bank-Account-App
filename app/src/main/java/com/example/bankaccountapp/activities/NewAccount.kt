package com.example.bankaccountapp.activities

import android.accounts.Account
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.RadioButton
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.CurrentAccount
import com.example.bankaccountapp.contas.SavingsAccount
import com.example.bankaccountapp.utils.Csv
import com.example.bankaccountapp.utils.toSHA256
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class NewAccount : AppCompatActivity() {


    private lateinit var nomeCompleto: EditText
    private lateinit var password: EditText
    private lateinit var buttonCreateAccount : View
    private lateinit var radioCorrente : RadioButton
    private lateinit var radioPoupanca : RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        nomeCompleto = findViewById(R.id.create_account_nomecompleto)
        password = findViewById(R.id.create_account_senha)
        buttonCreateAccount = findViewById(R.id.create_account_button)
        radioCorrente = findViewById(R.id.radio_conta_corrente)
        radioPoupanca = findViewById(R.id.radio_conta_poupança)


        buttonCreateAccount.setOnClickListener{
            val nome = nomeCompleto.text.toString()
            val senha = password.text.toString()
            if(nome.isEmpty() || senha.isEmpty()){

            }
            else{
//                val accountType = accountType()
                val accountnumber = newAccountNumber()
                val conta = if (radioCorrente.isChecked){
                    CurrentAccount(accountnumber,nome,senha.toSHA256(),Calendar.getInstance().time,0L)
                }
                else{
                    SavingsAccount(accountnumber,nome,senha.toSHA256(),Calendar.getInstance().time,0L)
                }
                Csv.adicionarConta(conta)

                finish()
            }
        }
    }



    //arrumar account number

    private fun newAccountNumber(): Int {
        var maior = 0
        val contas = Csv.lerCsv()
        contas.forEach{
            if(it.accountNumber>maior){
                maior = it.accountNumber
            }
        }
        return maior+1
    }


//    private fun accountType(): String {
//        if(radioCorrente.isChecked){
//            return "Current Account"
//        }
//        else if(radioPoupanca.isChecked){
//            return "Savings Account"
//        }
//        return ""
//    }

}