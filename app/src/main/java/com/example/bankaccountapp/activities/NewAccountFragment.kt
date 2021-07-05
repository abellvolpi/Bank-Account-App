package com.example.bankaccountapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.CurrentAccount
import com.example.bankaccountapp.contas.SavingsAccount
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.toSHA256
import java.text.NumberFormat
import java.util.*

class NewAccountFragment : Fragment(R.layout.activity_new_account) {


    private lateinit var nomeCompleto: EditText
    private lateinit var password: EditText
    private lateinit var buttonCreateAccount : View
    private lateinit var radioCorrente : RadioButton
    private lateinit var radioPoupanca : RadioButton
    private lateinit var saldoInicial : EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
            nomeCompleto = findViewById(R.id.create_account_nomecompleto)
            password = findViewById(R.id.create_account_senha)
            buttonCreateAccount = findViewById(R.id.create_account_button)
            radioCorrente = findViewById(R.id.radio_conta_corrente)
            radioPoupanca = findViewById(R.id.radio_conta_poupança)
            saldoInicial = findViewById(R.id.saldo_inicial)
        }

        saldoInicial.addTextChangedListener(object : TextWatcher { // assiste enquanto o usuário digita
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { //alterações antes do texto mudar
            }

            var current = ""
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { //enquanto o texto está sendo escrito
                if (s.toString().isNotBlank()) {
                    saldoInicial.removeTextChangedListener(this)
                    val cleanString = s?.replace("""[$,.]""".toRegex(), "") ?: s.toString()
                    val parsed = cleanString.filter { it.isDigit() }.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                    current = formatted
                    saldoInicial.setText(formatted)
                    saldoInicial.setSelection(formatted.length)
                    saldoInicial.addTextChangedListener(this)
                }
            }
            override fun afterTextChanged(s: Editable?) { //depois do texto ser mudado
            }
        })




        buttonCreateAccount.setOnClickListener{

            val nome = nomeCompleto.text.toString()
            val senha = password.text.toString()
            val saldo = saldoInicial.text.toString()

            if(nome.isEmpty() || senha.isEmpty() || (!radioCorrente.isChecked && !radioPoupanca.isChecked)){
                val toast = Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT)
                toast.show()
            }

            else{

                if (radioCorrente.isChecked){
                           if(AccountManager.existeContaCorrente(nome,true)){
                                val toast = Toast.makeText(requireContext(), "Você já possui uma Conta Corrente", Toast.LENGTH_SHORT)
                                toast.show()
                           }
                           else{
                               AccountManager.adicionarConta(CurrentAccount(newAccountNumber(),nome,senha.toSHA256(),Calendar.getInstance().time,saldo.filter { it.isDigit() }.toLong()))
//                               finish()

                           }
                        }
                else {
                   if (AccountManager.existeContaCorrente(nome, false)) {
                       val toast = Toast.makeText(requireContext(),"Você já possui uma Conta Poupança", Toast.LENGTH_SHORT)
                       toast.show()
                   } else {
                       AccountManager.adicionarConta(SavingsAccount(newAccountNumber(), nome, senha.toSHA256(), Calendar.getInstance().time, saldo.filter { it.isDigit() }.toLong()))
//                       finish()

                   }
               }
            }
        }
    }


    private fun newAccountNumber(): Int {
        var maior = 0
        val contas = AccountManager.lerCsv()
        contas.forEach{
            if(it.accountNumber>maior){
                maior = it.accountNumber
            }
        }
        return maior+1
    }


}