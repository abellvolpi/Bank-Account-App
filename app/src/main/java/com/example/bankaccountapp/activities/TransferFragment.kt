package com.example.bankaccountapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankaccountapp.R
import com.example.bankaccountapp.models.Account
import com.example.bankaccountapp.databinding.FragmentTransferBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.balanceFormated
import java.io.File
import java.io.FileWriter
import java.text.NumberFormat
import java.util.*


class TransferFragment : Fragment(R.layout.fragment_transfer) {

    private val args: TransferFragmentArgs by navArgs()
    private lateinit var binding: FragmentTransferBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransferBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(binding) {

            saldoAtual.text = "Seu saldo Atual é de R$ ${balanceFormated(args.conta.balance)}"

            transfermoneyValue.addTextChangedListener(object :
                TextWatcher { // assiste enquanto o usuário digita
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { //alterações antes do texto mudar
                }

                var current = ""
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { //enquanto o texto está sendo escrito

                    if (s.toString().isNotBlank()) {
                        transfermoneyValue.removeTextChangedListener(this)
                        val cleanString = s?.replace("""[$,.]""".toRegex(), "") ?: s.toString()
                        val parsed = cleanString.filter { it.isDigit() }.toDouble()
                        val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                        current = formatted
                        transfermoneyValue.setText(formatted)
                        transfermoneyValue.setSelection(formatted.length)
                        transfermoneyValue.addTextChangedListener(this)
                    }
                }

                override fun afterTextChanged(s: Editable?) { //depois do texto ser mudado
                }
            })



            transferConfirm.setOnClickListener {
                if (transfermoneyValue.text.toString().isEmpty() || transferAccountId.text.toString().isEmpty() || transfermoneyValue.text.toString().filter { it.isDigit() }.toLong() == 0L
                ) {
                    Toast.makeText(requireContext(), "Preencha todos os Campos", Toast.LENGTH_SHORT).show()
                } else {
                    if (transfermoneyValue.text.toString().filter { it.isDigit() }.toLong() > args.conta.balance) {
                        Toast.makeText(requireContext(), "Saldo Insuficiente", Toast.LENGTH_SHORT).show()
                    } else {
                        if (contaExiste(transferAccountId.text.toString().toInt())) {
                            transference(args.conta, transferAccountId.text.toString().toInt(), transfermoneyValue.text.toString().filter { it.isDigit() }.toLong())
                            atualizartransferencias1(args.conta, transferAccountId.text.toString().toInt(), transfermoneyValue.text.toString().filter { it.isDigit() }.toLong())
                            atualizartransferencias2(args.conta, transferAccountId.text.toString().toInt(), transfermoneyValue.text.toString().filter { it.isDigit() }.toLong())
                        } else {
                            Toast.makeText(requireContext(), "Destinatário não Existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }


    fun transference(conta: Account, id: Int, valorTransferido: Long) {
        var contas = AccountManager.lerCsv()
        contas.forEach {
            if (it.accountNumber == conta.accountNumber) {
                it.balance -= valorTransferido
                contas.forEach {
                    if (it.accountNumber == id) {
                        it.balance += valorTransferido
                        findNavController().popBackStack()
                    }
                }
            }

        }
        AccountManager.escreverCsv()
    }

    fun contaExiste(id: Int): Boolean {
        var contas = AccountManager.lerCsv()
        contas.forEach {
            if (it.accountNumber == id) {
                return true
            }
        }
        return false
    }

    fun atualizartransferencias1(conta: Account, id: Int, valorTransferido: Long){
        val file = File(requireContext().cacheDir,"${conta.accountNumber}.csv") // ou requireContext().cachedir
        val fileWriter = FileWriter(file, true)
        fileWriter.append("${conta.accountNumber};Transferência enviada;${id};${valorTransferido};${Calendar.getInstance().time.time}\n")
        fileWriter.close()
    }
    fun atualizartransferencias2(conta: Account, id: Int, valorTransferido: Long){
        val file = File(requireContext().cacheDir,"${id}.csv") // ou requireContext().cachedir
        val fileWriter = FileWriter(file, true)
        fileWriter.append("${id};Transferência recebida;${conta.accountNumber};${valorTransferido};${Calendar.getInstance().time.time}\n")
        fileWriter.close()
    }





}