package mx.lifehealthsolutions.myhealthjournal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.renglon_entrada.view.*

class AdapterViewEntry(private val contexto: Context,var arrEntradas:Array<Entry>): RecyclerView.Adapter<AdapterViewEntry.RenglonEntrada>() {

    var listener: ListenerRecycler? = null

    inner class RenglonEntrada(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonEntrada {
        val vista = LayoutInflater.from(contexto).inflate(R.layout.renglon_entrada, parent, false)
        return RenglonEntrada(vista)
    }

    override fun getItemCount(): Int {
        return arrEntradas.size
    }

    override fun onBindViewHolder(holder: RenglonEntrada, position: Int) {
        val entrada = arrEntradas[position] // arrPaises.get(position)
        //holder.vistaRenglon.tvFechaEntrada = entrada.fecha
        //holder.vistaRenglon.tvTipoEntrada = entrada.tipo

        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }
}