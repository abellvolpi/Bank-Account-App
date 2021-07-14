package com.example.bankaccountapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankaccountapp.R
import com.example.bankaccountapp.databinding.FragmentManageMoneyBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.AccountManager.escreverCsv
import com.example.bankaccountapp.utils.balanceFormated
import com.example.bankaccountapp.utils.hideKeyboard
import com.example.bankaccountapp.utils.hideSoftKeyboard
import java.io.File
import java.io.FileWriter
import java.text.NumberFormat
import java.util.*

class ManageMoneyFragment : Fragment(R.layout.fragment_manage_money) {

    private val args: ManageMoneyFragmentArgs by navArgs()
    private lateinit var binding: FragmentManageMoneyBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentManageMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mensagem = args.acao
        var account = args.conta





        with(binding) {

            constraintLayoutManageMoney.setOnClickListener {
                activity?.hideSoftKeyboard()

            }


            toolbarManagemoney.setNavigationOnClickListener {
                activity?.onBackPressed()
            }

            when (mensagem) {
                "Digite o Valor do Depósito" -> toolbarManagemoney.title = "Depósito"
                "Digite o valor do Saque" -> toolbarManagemoney.title = "Saque"
            }

            managemoneyText.text = mensagem
            saldoAtual.text = "Seu saldo atual é de R$ ${balanceFormated(account.balance)}"


            managemoneyValor.addTextChangedListener(object :
                TextWatcher { // assiste enquanto o usuário digita
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { //alterações antes do texto mudar
                }

                var current = ""
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { //enquanto o texto está sendo escrito

                    if (s.toString().isNotBlank()) {
                        managemoneyValor.removeTextChangedListener(this)
                        val cleanString = s?.replace("""[$,.]""".toRegex(), "") ?: s.toString()
                        val parsed = cleanString.filter { it.isDigit() }.toDouble()
                        val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                        current = formatted
                        managemoneyValor.setText(formatted)
                        managemoneyValor.setSelection(formatted.length)
                        managemoneyValor.addTextChangedListener(this)
                    }
                }

                override fun afterTextChanged(s: Editable?) { //depois do texto ser mudado
                }
            })


            managemoneyConfirm.setOnClickListener {

                view.hideKeyboard()

                if (managemoneyValor.text.toString().isBlank()) {
                    val toast = Toast.makeText(requireContext(), "Digite um valor", Toast.LENGTH_SHORT)
                    toast.show()
                } else {

                    val contas = AccountManager.lerCsv()

                    if (mensagem == "Digite o Valor do Depósito") { //depositar
                        contas.forEach {
                            if (account.accountNumber == it.accountNumber) {
                                it.balance += managemoneyValor.text.toString().filter {
                                    it.isDigit() // pega somente os digitos, tirando os pontos e virgulas
                                }.toLong()
                                atualizaHistoricoDeposito(it.accountNumber, managemoneyValor.text.toString().filter {
                                    it.isDigit()
                                }.toLong())
                                findNavController().popBackStack()

                            }
                        }
                    } else { //sacar

                        if (account.balance < managemoneyValor.text.toString().filter {
                                it.isDigit()
                            }.toLong()) {

                        } else {
                            contas.forEach {
                                if (account.accountNumber == it.accountNumber) {
                                    it.balance -= managemoneyValor.text.toString().filter {
                                        it.isDigit()
                                    }.toLong()
                                    atualizaHistoricoSaque(it.accountNumber, managemoneyValor.text.toString().filter {
                                        it.isDigit()
                                    }.toLong())
                                    findNavController().popBackStack()

                                }
                            }
                        }
                    }
                    escreverCsv()
                }
            }

        }

    }

    fun atualizaHistoricoDeposito(id: Int, valor: Long) {
        val file = File(requireContext().cacheDir, "${id}.csv") // ou requireContext().cachedir
        val fileWriter = FileWriter(file, true)
        fileWriter.append("${id};Depósito;${id};${valor};${Calendar.getInstance().time.time}\n")
        fileWriter.close()
    }

    fun atualizaHistoricoSaque(id: Int, valor: Long) {
        val file = File(requireContext().cacheDir, "${id}.csv") // ou requireContext().cachedir
        val fileWriter = FileWriter(file, true)
        fileWriter.append("${id};Saque;${id};${valor};${Calendar.getInstance().time.time}\n")
        fileWriter.close()
    }
}

