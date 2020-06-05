package mx.lifehealthsolutions.myhealthjournal.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.renglon_entrada.view.*
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.R

class AdapterViewEntry(var arrEntradas:ArrayList<Entry>):
    RecyclerView.Adapter<AdapterViewEntry.RenglonEntrada>() {

    var listener: ListenerRecycler? = null

    init{
        notifyDataSetChanged()
    }

    inner class RenglonEntrada(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonEntrada {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.renglon_entrada, parent, false)
        return RenglonEntrada(vista)
    }

    override fun getItemCount(): Int {
        return arrEntradas.size
    }

    override fun onBindViewHolder(holder: RenglonEntrada, position: Int) {
        val entrada = arrEntradas[position]

        holder.vistaRenglon.tvFechaEntrada.text = entrada.date.toString()

        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }
}