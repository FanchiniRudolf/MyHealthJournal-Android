package mx.lifehealthsolutions.myhealthjournal.models

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.renglon_sintoma.view.*
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.R

class AdapterViewCondition(var email: String?): RecyclerView.Adapter<AdapterViewCondition.RenglonCondicion>() {
    var listener: ListenerRecycler? = null
    var conditions:Array<Condition>? =  null
    var arrCondiciones: Array<Condition> ?= null
    var arrConditions  =  mutableListOf<Condition>()

    init {

        notifyDataSetChanged()
        arrCondiciones  = arrayOf(
            Condition("Asma", "asdaasd"),
            Condition("Migraña", "dsadas"),
            Condition("COVID-19", "asdas")
        )
        downloadConditions()
    }

    fun downloadConditions() {
        val db = FirebaseFirestore.getInstance()
        var userStr =  "${User.email}"
        db.collection("Users/$userStr/Conditions")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    var temp = document.data.get("description").toString()

                    arrConditions.add(Condition(document.id, temp))
                }
                notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
        arrCondiciones = arrConditions.toTypedArray<Condition>()
        notifyDataSetChanged()
    }

    inner class RenglonCondicion(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonCondicion {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.renglon_sintoma, parent, false)
        return RenglonCondicion(vista)
    }

    override fun getItemCount(): Int {
        return arrCondiciones?.size!!

    }

    override fun onBindViewHolder(holder: RenglonCondicion, position: Int) {
        val condicion = arrCondiciones?.get(position) // arrPaises.get(position)
        holder.vistaRenglon.tvSintoma.text = condicion?.name
        holder.vistaRenglon.shortSintoma.text = condicion?.name?.take(1)
        holder.vistaRenglon.tvDetalleSintoma.text = condicion?.description
        //holder.vistaRenglon.tInputSintoma.toString()
        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }

}