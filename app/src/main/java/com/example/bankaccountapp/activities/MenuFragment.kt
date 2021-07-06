package com.example.bankaccountapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.Account
import com.example.bankaccountapp.databinding.FragmentLoginBinding
import com.example.bankaccountapp.databinding.FragmentManageMoneyBinding
import com.example.bankaccountapp.databinding.FragmentMenuBinding
import com.example.bankaccountapp.utils.replaceFragment

const val STRING = "STRING"

class MenuFragment : Fragment(R.layout.fragment_menu) {


    lateinit var preferences: SharedPreferences
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var account = arguments?.getSerializable(ACCOUNT) as Account


//        val startForResult =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                // result: ActivityResult -> só serve para definir o "nome" do it
//                if (it.resultCode == Activity.RESULT_OK) {
//                    val intent = it.data?.extras?.getSerializable("novoSaldo")
//                        ?: ""  //?: se for nulo, realiza a string dps
////                    account = intent as Account
//                }
//            }
        // result: ActivityResult -> classe que vem com os dados da tela


        preferences = requireContext().getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        with(binding) {


            buttondepositar.setOnClickListener {

                replaceFragment(
                    ManageMoneyFragment.newInstance(account, "Digite o Valor do Depósito"),
                    R.id.fragment_container_view
                )

            }


            buttonSacar.setOnClickListener {

                replaceFragment(
                    ManageMoneyFragment.newInstance(account, "Deposite o valor do Saque"),
                    R.id.fragment_container_view
                )

            }

            buttonLogout.setOnClickListener {

                val editor: SharedPreferences.Editor = preferences.edit()
                editor.clear()
                editor.apply()

//            var ft = activity?.supportFragmentManager?.beginTransaction()
//            ft?.replace(R.id.fragment_container_view, LoginFragment())
//            ft?.commit()

                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_view, LoginFragment())?.commit()
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
        fun newInstance(account: Account) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ACCOUNT, account)
                }
            }
    }

}
