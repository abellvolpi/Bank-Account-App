package com.example.bankaccountapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankaccountapp.R
import com.example.bankaccountapp.models.Historic
import com.example.bankaccountapp.utils.HistoricManager
import com.example.bankaccountapp.utils.balanceFormated
import com.example.bankaccountapp.utils.formatDateToHHMMYYYYHHMM
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(private val historico: ArrayList<Historic>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nome: TextView = view.findViewById(R.id.nome)
        var titulo: TextView = view.findViewById(R.id.titulo)
        var valor: TextView = view.findViewById(R.id.valor)
        var data: TextView = view.findViewById(R.id.data)
        var image: ImageView = view.findViewById(R.id.operation_image)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.historic_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.titulo.text = historico[position].operacao

        if(historico[position].operacao == "Saque" || historico[position].operacao == "Depósito" ){
            viewHolder.nome.visibility = View.GONE
        }
        else {
            viewHolder.nome.text = HistoricManager.destino(historico[position].id2.toInt())
        }
        viewHolder.valor.text = "R$ ${balanceFormated(historico[position].valor.toLong())}"
        viewHolder.data.text = formatDateToHHMMYYYYHHMM(Date(historico[position].data.toLong())).uppercase()
        when (historico[position].operacao) {

            "Transferência recebida" -> viewHolder.image.setImageResource(R.drawable.ic_deposit)
            "Saque" -> viewHolder.image.setImageResource(R.drawable.ic_coinhand)
            "Depósito" -> viewHolder.image.setImageResource(R.drawable.ic_money)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = historico.size

}