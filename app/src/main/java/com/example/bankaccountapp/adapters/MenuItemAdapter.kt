package com.example.bankaccountapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bankaccountapp.R
import com.example.bankaccountapp.activities.MenuFragment
import com.example.bankaccountapp.activities.MenuFragmentArgs
import com.example.bankaccountapp.activities.MenuFragmentDirections
import com.example.bankaccountapp.databinding.MenuItensBinding
import com.example.bankaccountapp.models.MenuItem
import com.example.bankaccountapp.utils.AccountManager
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MenuItemAdapter(var context: Context, private val menuItens: ArrayList<MenuItem>, var args: MenuFragmentArgs) : RecyclerView.Adapter<MenuItemAdapter.ItemHolder>(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()

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
                    "Serviços" -> {
                        openDialog(it)
//                        it.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToTicTacToeFragment(args.conta))
                    }
                    "Gerador de Contas" ->{
                        launch(Dispatchers.Main) {
                             val result = async(Dispatchers.IO) {
                                 AccountManager.accountGenerator()
                             }
                            progressMenu.visibility = View.VISIBLE
                            result.await()
                            progressMenu.visibility = View.GONE
                        }

                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return menuItens.size
    }

    fun openDialog(view: View){

        var builder = AlertDialog.Builder(context)
        builder.setView(View.inflate(context, R.layout.dialog_layout, null))


        builder.setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
            run {
                view.findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToTicTacToeFragment(args.conta))
                dialog.dismiss()
            }
        })

        builder.setNegativeButton("cancelar", DialogInterface.OnClickListener() { dialog: DialogInterface?, which: Int ->
            dialog?.dismiss()
        })


        var alertDialog = builder.create()
        alertDialog.show()

    }


}