package com.example.bankaccountapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.utils.Csv
import com.example.bankaccountapp.utils.Csv.escreverCsv
import org.w3c.dom.Text
import java.text.NumberFormat

class ManageMoneyActivity : AppCompatActivity() {

    private lateinit var managemoneyText: TextView
    private lateinit var confirmAction: LinearLayout
    private lateinit var saldoAtual: TextView
    private lateinit var valor: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_money)

        valor = findViewById(R.id.managemoney_valor)
        confirmAction = findViewById(R.id.managemoney_confirm)
        managemoneyText = findViewById(R.id.managemoney_text)
        saldoAtual = findViewById(R.id.saldo_atual)

        val mensagem = intent.getSerializableExtra(STRING) as String
        var account = intent.getSerializableExtra(ACCOUNT) as Account
        managemoneyText.text = mensagem
        saldoAtual.text = "Seu saldo atual é de R$ ${account.balance}"

        valor.addTextChangedListener(object : TextWatcher { // assiste enquanto o usuário digita
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { //alterações antes do texto mudar
            }

            var current = ""
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { //enquanto o texto está sendo escrito
                if (s.toString().isNotBlank()) {
                    valor.removeTextChangedListener(this)
                    val cleanString = s?.replace("""[$,.]""".toRegex(), "") ?: s.toString()
                    val parsed = cleanString.filter { it.isDigit() }.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                    current = formatted
                    valor.setText(formatted)
                    valor.setSelection(formatted.length)
                    valor.addTextChangedListener(this)
                }
            }
            override fun afterTextChanged(s: Editable?) { //depois do texto ser mudado
            }
        })


        confirmAction.setOnClickListener {

            if (valor.text.toString().isBlank()) {
                val toast =
                    Toast.makeText(this, "Digite um valor", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val contas = Csv.lerCsv()
                if (mensagem == "Digite o Valor do Depósito") { //depositar
                    contas.forEach {
                        if (account.accountNumber == it.accountNumber) {
                            it.balance += valor.text.toString().filter {
                                it.isDigit() // pega somente os digitos, tirando os pontos e virgulas
                            }.toLong()

                            var resultIntent = Intent().putExtra("novoSaldo", it)
                            setResult(
                                Activity.RESULT_OK,
                                resultIntent
                            ) // activity result ok é apenas um id de tipo int para identificação do valor que está vindo dessa classe
                            finish()
                        }
                    }
                } else { //sacar
                    if (account.balance < valor.text.toString().filter {
                            it.isDigit()
                        }.toLong()) {

                    } else {
                        contas.forEach {
                            if (account.accountNumber == it.accountNumber) {
                                it.balance -= valor.text.toString().filter {
                                    it.isDigit()
                                }.toLong()

                                var resultIntent = Intent().putExtra("novoSaldo", it)
                                setResult(
                                    Activity.RESULT_OK,
                                    resultIntent
                                ) // activity result ok é apenas um id de tipo int para identificação do valor que está vindo dessa classe
                                finish()

                            }
                        }
                    }
                }
                escreverCsv()
                finish()
            }
        }
    }
}
