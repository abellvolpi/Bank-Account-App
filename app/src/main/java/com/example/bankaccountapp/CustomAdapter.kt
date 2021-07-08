package com.example.bankaccountapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankaccountapp.utils.balanceFormated
import com.example.bankaccountapp.utils.formatDateToHHMMYYYYHHMM
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter (private val historico: ArrayList<Historic>): RecyclerView.Adapter<CustomAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nome: TextView = view.findViewById(R.id.nome)
        var titulo: TextView = view.findViewById(R.id.titulo)
        var valor : TextView = view.findViewById(R.id.valor)
        var data: TextView = view.findViewById(R.id.data)

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
        viewHolder.nome.text = historico[position].id2
        viewHolder.titulo.text = historico[position].operacao
        viewHolder.valor.text = "R$ ${balanceFormated(historico[position].valor.toLong())}"
        viewHolder.data.text = formatDateToHHMMYYYYHHMM(Date(historico[position].data.toLong())).uppercase()


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = historico.size

}