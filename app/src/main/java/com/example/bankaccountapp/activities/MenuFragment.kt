package com.example.bankaccountapp.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankaccountapp.R
import com.example.bankaccountapp.adapters.MenuItemAdapter

import com.example.bankaccountapp.databinding.FragmentMenuBinding
import com.example.bankaccountapp.models.MenuItem

const val STRING = "STRING"

class MenuFragment : Fragment(R.layout.fragment_menu) {



    private val args: MenuFragmentArgs by navArgs()
    lateinit var preferences: SharedPreferences
    private lateinit var binding: FragmentMenuBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireContext().getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE)

        with(binding) {


            menuRecyclerView.adapter = MenuItemAdapter(requireContext(),setMenuOptions(),args)
            menuRecyclerView.layoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)
            menuRecyclerView.setHasFixedSize(true)

        }

    }
    private  fun setMenuOptions(): ArrayList<MenuItem>{

        var menuItens: ArrayList<MenuItem> = ArrayList()

        menuItens.add(MenuItem(R.drawable.ic_moneypack,"Depositar"))
        menuItens.add(MenuItem(R.drawable.ic_coinhand,"Sacar"))
        menuItens.add(MenuItem(R.drawable.ic_currencies,"Transferir"))
        menuItens.add(MenuItem(R.drawable.ic_historic,"Histórico"))
        menuItens.add(MenuItem(R.drawable.ic_services,"Serviços"))
        menuItens.add(MenuItem(R.drawable.ic_shield,"Privacidade"))
        menuItens.add(MenuItem(R.drawable.ic_logout,"Logout"))

        return menuItens

    }

}