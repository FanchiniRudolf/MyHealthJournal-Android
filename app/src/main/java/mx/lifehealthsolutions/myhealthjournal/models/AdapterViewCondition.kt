package mx.lifehealthsolutions.myhealthjournal.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.R

class AdapterViewCondition(var contexto: FragmentActivity?, var arrCondiciones:Array<Condition>): RecyclerView.Adapter<AdapterViewCondition.RenglonCondicion>() {
    var listener: ListenerRecycler? = null

    inner class RenglonCondicion(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonCondicion {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.renglon_sintoma, parent, false)
        return RenglonCondicion(vista)
    }

    override fun getItemCount(): Int {
        return arrCondiciones.size
    }

    override fun onBindViewHolder(holder: RenglonCondicion, position: Int) {
        val entrada = arrCondiciones[position] // arrPaises.get(position)
        //holder.vistaRenglon.tvSintoma = entrada.fecha
        //holder.vistaRenglon.tInputSintoma.toString()
        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }

}