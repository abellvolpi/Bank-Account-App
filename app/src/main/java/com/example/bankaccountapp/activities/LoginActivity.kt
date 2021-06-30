package com.example.bankaccountapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.utils.Csv
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileWriter

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: View
    private lateinit var progress: ProgressBar
    private lateinit var buttonNewAccount: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.username_field)
        password = findViewById(R.id.password_field)
        buttonLogin = findViewById(R.id.buttonLogin)
        progress = findViewById(R.id.progress)
        buttonNewAccount = findViewById(R.id.button_newAccount)

        val file = File(cacheDir, "accounts.csv")
        val fileWriter = FileWriter(file, true)
        fileWriter.append("19;joao;senha;30/06/2021;0")
        fileWriter.close()


//        buttonLogin.setOnClickListener {
//            changeState(true)
//            delay {
//                efetuaLogin(username.text.toString(), password.text.toString())?.let {
//                    val intencao = Intent(this, MenuActivity::class.java).apply {
//                    }
//                    startActivity(intencao)
//                    finish()
//                } ?: run {
//                    loginInvalido()
//                    changeState(false)
//
//                }
//            }
//        }

        buttonNewAccount.setOnClickListener {
            val intencao = Intent(this, NewAccount::class.java).apply {
            }
            startActivity(intencao)
        }
    }


    private fun changeState(isLoading: Boolean) {
        if (isLoading) {
            progress?.visibility = View.VISIBLE
            buttonLogin.apply {
                isClickable = false
                isFocusable = false
                alpha = 0.5f
            }
        } else {
            progress?.visibility = View.GONE
            buttonLogin.apply {
                isClickable = true
                isFocusable = true
                alpha = 1f
            }
        }
    }


    private fun delay(delay: Long = 1500, action: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(action, delay)
    }

    private fun loginInvalido() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro")
        builder.setMessage("Usuário ou senha inválidos")
        builder.setNeutralButton("OK") { _, _ -> }
        val caixa_dialogo: AlertDialog = builder.create()
        caixa_dialogo.show()

    }

//    private fun efetuaLogin(user_informed: String, password_informed: String): Account? {
////        val contas = Csv.lerCsv(this)
//        contas.forEach {
//            if (it.ownersName == user_informed && it.password == password_informed) {
//                return it
//            }
//        }
//        return null
//    }


}