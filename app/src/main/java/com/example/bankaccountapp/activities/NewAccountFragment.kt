package com.example.bankaccountapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bankaccountapp.R
import com.example.bankaccountapp.models.CurrentAccount
import com.example.bankaccountapp.models.SavingsAccount
import com.example.bankaccountapp.databinding.FragmentNewAccountBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.toSHA256
import java.text.NumberFormat
import java.util.*

class NewAccountFragment : Fragment(R.layout.fragment_new_account) {


    private lateinit var binding: FragmentNewAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            saldoInicial.addTextChangedListener(object :
                TextWatcher { // assiste enquanto o usuário digita
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) { //alterações antes do texto mudar
                }

                var current = ""
                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) { //enquanto o texto está sendo escrito
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




            buttonCreateAccount.setOnClickListener {

                val nome = nomeCompleto.text.toString()
                val senha = password.text.toString()
                val saldo = saldoInicial.text.toString()

                if (nome.isEmpty() || senha.isEmpty() || (!radioContaCorrente.isChecked && !radioContaPoupanca.isChecked) || saldo.isEmpty()) {
                    val toast = Toast.makeText(
                        requireContext(),
                        "Preencha todos os campos",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {

                    if (radioContaCorrente.isChecked) {

                        if (AccountManager.existeContaCorrente(nome, true)) {
                            val toast = Toast.makeText(
                                requireContext(),
                                "Você já possui uma Conta Corrente",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else {
                            AccountManager.adicionarConta(
                                CurrentAccount(
                                    newAccountNumber(),
                                    nome,
                                    senha.toSHA256(),
                                    Calendar.getInstance().time,
                                    saldo.filter { it.isDigit() }.toLong()
                                )
                            )
//                            replaceFragment(LoginFragment(), R.id.fragment_container_view)
                            findNavController().popBackStack()

                        }
                    } else {
                        if (AccountManager.existeContaCorrente(nome, false)) {
                            val toast = Toast.makeText(
                                requireContext(),
                                "Você já possui uma Conta Poupança",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else {
                            AccountManager.adicionarConta(
                                SavingsAccount(
                                    newAccountNumber(),
                                    nome,
                                    senha.toSHA256(),
                                    Calendar.getInstance().time,
                                    saldo.filter { it.isDigit() }.toLong()
                                )
                            )
//                            replaceFragment(LoginFragment(), R.id.fragment_container_view)

                            findNavController().popBackStack()
                        }
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