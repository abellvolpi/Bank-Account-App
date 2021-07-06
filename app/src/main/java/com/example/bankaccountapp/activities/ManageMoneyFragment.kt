package com.example.bankaccountapp.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.databinding.FragmentLoginBinding
import com.example.bankaccountapp.databinding.FragmentManageMoneyBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.AccountManager.escreverCsv
import com.example.bankaccountapp.utils.balanceFormated
import com.example.bankaccountapp.utils.popBackStack
import java.text.NumberFormat

class ManageMoneyFragment : Fragment(R.layout.fragment_manage_money) {


    private lateinit var binding: FragmentManageMoneyBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



            val mensagem = arguments?.getSerializable(STRING) as String
            var account = arguments?.getSerializable(ACCOUNT) as Account


        with(binding) {

            managemoneyText.text = mensagem
            saldoAtual.text = "Seu saldo atual é de R$ ${balanceFormated(account.balance)}"


            managemoneyValor.addTextChangedListener(object :
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
                        binding.managemoneyValor.removeTextChangedListener(this)
                        val cleanString = s?.replace("""[$,.]""".toRegex(), "") ?: s.toString()
                        val parsed = cleanString.filter { it.isDigit() }.toDouble()
                        val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))
                        current = formatted
                        binding.managemoneyValor.setText(formatted)
                        binding.managemoneyValor.setSelection(formatted.length)
                        binding.managemoneyValor.addTextChangedListener(this)
                    }
                }
                override fun afterTextChanged(s: Editable?) { //depois do texto ser mudado
                }
            })


            managemoneyConfirm.setOnClickListener {

                if (managemoneyValor.text.toString().isBlank()) {
                    val toast =
                        Toast.makeText(requireContext(), "Digite um valor", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    val contas = AccountManager.lerCsv()
                    if (mensagem == "Digite o Valor do Depósito") { //depositar
                        contas.forEach {
                            if (account.accountNumber == it.accountNumber) {
                                it.balance += managemoneyValor.text.toString().filter {
                                    it.isDigit() // pega somente os digitos, tirando os pontos e virgulas
                                }.toLong()

                                popBackStack() //encerra o fragmento atual
//                            replaceFragment(MenuFragment.newInstance(account),R.id.fragment_container_view)
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
                                    popBackStack()
                                }
                            }
                        }
                    }
                    escreverCsv()
//                replaceFragment(MenuFragment(),R.id.fragment_container_view)
                }
            }

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Test3Fragment.
         */
        @JvmStatic
        fun newInstance(account: Account, mensagem: String) =
            ManageMoneyFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT, account)
                    putString(STRING, mensagem)
                }
            }
    }
}

