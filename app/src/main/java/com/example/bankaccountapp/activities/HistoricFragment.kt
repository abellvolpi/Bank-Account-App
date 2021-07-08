package com.example.bankaccountapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankaccountapp.CustomAdapter
import com.example.bankaccountapp.R
import com.example.bankaccountapp.contas.CurrentAccount
import com.example.bankaccountapp.contas.SavingsAccount
import com.example.bankaccountapp.databinding.FragmentHistoricBinding
import com.example.bankaccountapp.utils.AccountManager
import com.example.bankaccountapp.utils.HistoricManager
import com.example.bankaccountapp.utils.MainApplication
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


class HistoricFragment : Fragment(R.layout.fragment_historic) {


    private val args: HistoricFragmentArgs by navArgs()
    private lateinit var binding: FragmentHistoricBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoricBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            recyclerView.adapter = CustomAdapter(HistoricManager.lerCsvHistorico(args.conta.accountNumber))
            recyclerView.layoutManager = LinearLayoutManager(requireContext())


        }

    }




}