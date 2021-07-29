package com.example.bankaccountapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.bankaccountapp.R
import com.example.bankaccountapp.models.Account
import com.example.bankaccountapp.databinding.FragmentLoginBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.hideSoftKeyboard
import com.example.bankaccountapp.utils.toSHA256
import kotlinx.coroutines.*
import java.io.File
import java.io.FileWriter
import kotlin.coroutines.CoroutineContext

const val ACCOUNT = "ACCOUNT"

class LoginFragment : Fragment(R.layout.fragment_login), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentLoginBinding

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {


            sharedPreferences = requireContext().getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

            val file = File(activity?.applicationContext?.cacheDir, "accounts.csv") // ou requireContext().cachedir
            val fileWriter = FileWriter(file, true)
            fileWriter.close()

            val contaRegistradaId = sharedPreferences.getString("ID", "").toString()

            if (contaRegistradaId.isNotEmpty()) {

                val contas = AccountManager.lerCsv()
                contas.forEach {
                    if (it.accountNumber == contaRegistradaId.toInt()) {

                        val action = LoginFragmentDirections.actionLoginFragmentToMenuFragment(it)
                        navController.navigate(action)
                    }
                }

            }

            buttonLogin.setOnClickListener {

                launch(Dispatchers.IO) {
                    efetuaLogin(usernameField.text.toString(), passwordField.text.toString().toSHA256())?.let {
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("ID", it.accountNumber.toString())
                        editor.apply()
                        withContext(Dispatchers.Main) {
                            navController.navigate(LoginFragmentDirections.actionLoginFragmentToMenuFragment(it))
                        }
                    } ?: run {
                        loginInvalido()
                    }
                }
            }


//            buttonLogin.setOnClickListener {
//                changeState(true)
//                delay {
//                    efetuaLogin(
//                        usernameField.text.toString(),
//                        passwordField.text.toString().toSHA256()
//                    )?.let {
//
//                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                        editor.putString("ID", it.accountNumber.toString())
//                        editor.apply()
//
//                        val action = LoginFragmentDirections.actionLoginFragmentToMenuFragment(it)
//                        navController.navigate(action)
////                        navController.navigate(R.id.action_loginFragment_to_menuFragment, bundleOf(Pair(ACCOUNT, it)))
//
//
//                    } ?: run {
//
//                        loginInvalido()
//                        changeState(false)
//
//                    }
//                }
//            }

            buttonNewAccount.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_newAccountFragment)
            }

            constraintLayoutLogin.setOnClickListener {
                activity?.hideSoftKeyboard()
            }
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


    private suspend fun loginInvalido() {
        withContext(Dispatchers.Main) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Erro")
            builder.setMessage("Usuário ou senha inválidos")
            builder.setNeutralButton("OK") { _, _ -> }
            val caixa_dialogo: AlertDialog = builder.create()
            caixa_dialogo.show()
        }
    }


//    private fun efetuaLogin(user_informed: String, password_informed: String): Account? {
//        val contas = AccountManager.lerCsv()
//        contas.forEach {
//            if (it.ownersName == user_informed && it.password == password_informed) {
//                return it
//            }
//        }
//        return null
//    }

    private suspend fun efetuaLogin(user_informed: String, password_informed: String) = withContext(Dispatchers.IO){
        val contas = AccountManager.lerCsv()
        contas.forEach {
            if (it.ownersName == user_informed && it.password == password_informed) {
                return@withContext it
            }
        }
        null
    }



}