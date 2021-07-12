package com.example.bankaccountapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankaccountapp.adapters.CustomAdapter
import com.example.bankaccountapp.R
import com.example.bankaccountapp.databinding.FragmentHistoricBinding
import com.example.bankaccountapp.utils.HistoricManager


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


            toolbarHistoric.setNavigationOnClickListener {
                activity?.onBackPressed()
            }

            if(HistoricManager.lerCsvHistorico(args.conta.accountNumber)==null){
                historicoVazio.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            else {
                recyclerView.adapter = HistoricManager.lerCsvHistorico(args.conta.accountNumber)?.let { CustomAdapter(it) }
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            }
        }

    }
}