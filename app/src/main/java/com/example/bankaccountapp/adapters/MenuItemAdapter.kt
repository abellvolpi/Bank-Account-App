package com.example.bankaccountapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bankaccountapp.R
import com.example.bankaccountapp.activities.MenuFragmentArgs
import com.example.bankaccountapp.activities.MenuFragmentDirections
import com.example.bankaccountapp.models.MenuItem

class MenuItemAdapter(var context: Context, private val menuItens: ArrayList<MenuItem>, var args: MenuFragmentArgs) : RecyclerView.Adapter<MenuItemAdapter.ItemHolder>() {




    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var menuText: TextView = itemView.findViewById(R.id.menu_texts)
        var menuIcon: ImageView = itemView.findViewById(R.id.menu_icons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val itemHolder = LayoutInflater.from(parent.context).inflate(R.layout.menu_itens, parent, false)
        return ItemHolder(itemHolder)

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var currentItem : MenuItem = menuItens.get(position)

        holder.menuIcon.setImageResource((currentItem.menuIcons!!))
        holder.menuText.text = currentItem.menuText

        holder.itemView.setOnClickListener{
            Toast.makeText(context,currentItem.menuText,Toast.LENGTH_LONG).show()

            when(currentItem.menuText){
                "Depositar" ->
                    it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToManageMoneyFragment(args.conta,"Digite o Valor do Depósito"))
//                "Sacar" ->
//                "Transferir" ->
//                "Histórico" ->
//                "Logout" ->
            }


//            buttondepositar.setOnClickListener {
//
//                val action = MenuFragmentDirections.actionMenuFragmentToManageMoneyFragment(args.conta,"Digite o Valor do Depósito")
//                navController.navigate(action)
//
//            }


        }

    }

    override fun getItemCount(): Int {
        return menuItens.size
    }


}