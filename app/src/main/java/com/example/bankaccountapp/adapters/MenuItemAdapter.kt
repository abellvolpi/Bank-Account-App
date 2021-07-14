package com.example.bankaccountapp.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bankaccountapp.R
import com.example.bankaccountapp.activities.MenuFragmentArgs
import com.example.bankaccountapp.activities.MenuFragmentDirections
import com.example.bankaccountapp.databinding.FragmentMenuBinding
import com.example.bankaccountapp.databinding.MenuItensBinding
import com.example.bankaccountapp.models.MenuItem

class MenuItemAdapter(var context: Context, private val menuItens: ArrayList<MenuItem>, var args: MenuFragmentArgs) : RecyclerView.Adapter<MenuItemAdapter.ItemHolder>() {

    private lateinit var binding: MenuItensBinding

    class ItemHolder(itemView: MenuItensBinding) : RecyclerView.ViewHolder(itemView.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        binding = MenuItensBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var currentItem: MenuItem = menuItens.get(position)

        with(binding) {

            menuIcons.setImageResource((currentItem.menuIcons!!))
            menuTexts.text = currentItem.menuText

            holder.itemView.setOnClickListener {

                when (currentItem.menuText) {
                    "Depositar" ->
                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToManageMoneyFragment(args.conta, "Digite o Valor do Depósito"))
                    "Sacar" ->
                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToManageMoneyFragment(args.conta, "Digite o valor do Saque"))
                    "Transferir" ->
                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToTransferFragment(args.conta))
                    "Histórico" ->
                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToHistoricFragment(args.conta))
                    "Logout" -> {
                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToLoginFragment())
                        it.context.getSharedPreferences("CREDENCIAIS", Context.MODE_PRIVATE).edit().clear().apply()
                    }
                    "Serviços"->
                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToTicTacToeFragment())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuItens.size
    }


}