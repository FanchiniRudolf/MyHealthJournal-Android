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
            Condition("Asma"),
            Condition("Migraña"),
            Condition("COVID-19")
        )
        downloadConditions()
    }

    fun downloadConditions() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser?.email
        var userStr =  "{${email}}"
        var hola  = ""
        db.collection("Users/$userStr/Conditions")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    var temp = document.id
                    arrConditions.add(Condition(document.id))
                    hola = "asda"
                }
                notifyDataSetChanged()
                var temp =  "Hello World"
                println(temp)
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
        //holder.vistaRenglon.tInputSintoma.toString()
        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }

}