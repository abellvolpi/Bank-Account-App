package com.example.bankaccountapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.databinding.FragmentLoginBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.toSHA256
import java.io.File
import java.io.FileWriter

const val ACCOUNT = "ACCOUNT"

class LoginFragment : Fragment(R.layout.fragment_login) {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        sharedPreferences = requireContext().getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)


//        view.apply {
//            username = findViewById(R.id.username_field)
//            password = findViewById(R.id.password_field)
//            progress = findViewById(R.id.progress)
//            buttonNewAccount = findViewById(R.id.button_newAccount)
//        }


        val file = File( activity?.applicationContext?.cacheDir, "accounts.csv") // ou requireContext().cachedir
        val fileWriter = FileWriter(file, true)
        fileWriter.close()


        val contaRegistradaId = sharedPreferences.getString("ID", "").toString()


        if (contaRegistradaId.isNotEmpty()) {
            var conta: Account
            val contas = AccountManager.lerCsv()
            contas.forEach {
                if (it.accountNumber == contaRegistradaId.toInt()) {
                    conta = it
                    var ft = activity?.supportFragmentManager?.beginTransaction()
                    val bundle = bundleOf(Pair(ACCOUNT,conta))
                    ft?.replace(R.id.fragment_container_view, MenuFragment::class.java,bundle)
                    ft?.commit()
                }
            }
        }


        binding.buttonLogin.setOnClickListener {
            changeState(true)
            delay {
                efetuaLogin(binding.usernameField.text.toString(), binding.passwordField.text.toString().toSHA256())?.let {

                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("ID", it.accountNumber.toString())
                    editor.apply()

                    var ft = activity?.supportFragmentManager?.beginTransaction()
                    val bundle = bundleOf(Pair(ACCOUNT,it))
                    ft?.replace(R.id.fragment_container_view, MenuFragment::class.java,bundle)
                    ft?.commit()


                } ?: run {

                    loginInvalido()
                    changeState(false)

                }
            }
        }

        binding.buttonNewAccount.setOnClickListener {
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
            binding.progress?.visibility = View.VISIBLE
            binding.buttonLogin.apply {
                isClickable = false
                isFocusable = false
                alpha = 0.5f
            }
        } else {
            binding.progress?.visibility = View.GONE
            binding.buttonLogin.apply {
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