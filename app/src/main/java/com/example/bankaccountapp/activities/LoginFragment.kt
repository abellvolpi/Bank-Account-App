package com.example.bankaccountapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.toSHA256
import java.io.File
import java.io.FileWriter

const val ACCOUNT = "ACCOUNT"

class LoginFragment : Fragment(R.layout.activity_login) {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var buttonLogin: View
    private lateinit var progress: ProgressBar
    private lateinit var buttonNewAccount: View
    private lateinit var sharedPreferences: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedPreferences = requireContext().getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        view.apply {
            username = findViewById(R.id.username_field)
            password = findViewById(R.id.password_field)
            buttonLogin = findViewById(R.id.buttonLogin)
            progress = findViewById(R.id.progress)
            buttonNewAccount = findViewById(R.id.button_newAccount)
        }


        val file = File( activity?.applicationContext?.cacheDir, "accounts.csv") // ou requireContext().cachedir
        val fileWriter = FileWriter(file, true)
        fileWriter.close()


        val contaRegistradaId = sharedPreferences.getString("ID", "").toString()


//        if (contaRegistradaId.isNotEmpty()) {
//            var conta: Account
//            val contas = AccountManager.lerCsv()
//            contas.forEach {
//                if (it.accountNumber == contaRegistradaId.toInt()) {
//                    conta = it
//
////                    val intencao = Intent(this, MenuActivity::class.java).apply {
////                        putExtra(ACCOUNT, conta)
////                    }
////                    startActivity(intencao)
//
//                    var ft = activity?.supportFragmentManager?.beginTransaction()
//                    ft?.replace(R.id.fragment_container_view, MenuFragment())
//                    ft?.commit()
//                }
//            }
//        }


        buttonLogin.setOnClickListener {
            changeState(true)
            delay {
                efetuaLogin(username.text.toString(), password.text.toString().toSHA256())?.let {

                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("ID", it.accountNumber.toString())
                    editor.apply()



                    var ft = activity?.supportFragmentManager?.beginTransaction()
                    ft?.replace(R.id.fragment_container_view, MenuFragment())
                    ft?.commit()

//                    val result =



//                    val intencao = Intent(this, MenuActivity::class.java).apply {
//                        putExtra(ACCOUNT, it)
//                    }
//                    startActivity(intencao)
//                    finish()

                } ?: run {

                    loginInvalido()
                    changeState(false)

                }
            }
        }

        buttonNewAccount.setOnClickListener {
//            val intencao = Intent(this, NewAccountFragment::class.java).apply {
//            }
//            startActivity(intencao)

            var ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.fragment_container_view, NewAccountFragment())
            ft?.commit()
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
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Erro")
        builder.setMessage("Usuário ou senha inválidos")
        builder.setNeutralButton("OK") { _, _ -> }
        val caixa_dialogo: AlertDialog = builder.create()
        caixa_dialogo.show()

    }


    private fun efetuaLogin(user_informed: String, password_informed: String): Account? {
        val contas = AccountManager.lerCsv()
        contas.forEach {
            if (it.ownersName == user_informed && it.password == password_informed) {
                return it
            }
        }
        return null
    }


}