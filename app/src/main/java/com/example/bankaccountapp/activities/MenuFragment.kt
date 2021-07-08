package com.example.bankaccountapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bankaccountapp.R

import com.example.bankaccountapp.databinding.FragmentMenuBinding

const val STRING = "STRING"

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val args: MenuFragmentArgs by navArgs()
    lateinit var preferences: SharedPreferences
    private lateinit var binding: FragmentMenuBinding
    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var account = args.conta


        preferences = requireContext().getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        with(binding) {


            buttondepositar.setOnClickListener {

                val action = MenuFragmentDirections.actionMenuFragmentToManageMoneyFragment(args.conta,"Digite o Valor do Depósito")
                navController.navigate(action)

            }

            buttonSacar.setOnClickListener {

                val action = MenuFragmentDirections.actionMenuFragmentToManageMoneyFragment(args.conta,"Digite o valor do Saque")
                navController.navigate(action)

            }

            buttonTransfer.setOnClickListener{

                val action = MenuFragmentDirections.actionMenuFragmentToTransferFragment(args.conta)
                navController.navigate(action)
            }

            buttonhistoric.setOnClickListener {

                val action = MenuFragmentDirections.actionMenuFragmentToHistoricFragment(args.conta)
                navController.navigate(action)

            }



            buttonLogout.setOnClickListener {

                val editor: SharedPreferences.Editor = preferences.edit()
                editor.clear()
                editor.apply()

                findNavController().navigate(R.id.action_menuFragment_to_loginFragment)

            }
        }

    }
}