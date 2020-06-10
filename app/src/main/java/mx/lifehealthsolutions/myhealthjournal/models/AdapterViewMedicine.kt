package mx.lifehealthsolutions.myhealthjournal.models

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.renglon_medicina.view.*
import kotlinx.android.synthetic.main.renglon_sintoma.view.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler

class AdapterViewMedicine(var email: String?): RecyclerView.Adapter<AdapterViewMedicine.RenglonMedicina>() {
    var listener: ListenerRecycler? = null
    var medicines:Array<Medicine>? =  null
    var arrMedicinas: Array<Medicine> ?= null
    var arrMedicines  =  mutableListOf<Medicine>()

    init {

        notifyDataSetChanged()
        arrMedicinas  = arrayOf(
            Medicine("4", "06-09-2020",  "06-09-2020", "Paracetamlo", "Migraña")
        )
        downloadMedicines()
    }

    private fun downloadMedicines() {
        val db = FirebaseFirestore.getInstance()
        var userStr =  email

        db.collection("Users/$userStr/Medicines")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val freq = document.data.get("frequency").toString()
                    val start_date = document.data.get("treatment_start").toString()
                    val finish_date = document.data.get("treatment_finish").toString()
                    val name = document.data.get("medicine").toString()
                    val condition = document.data.get("medicine").toString()
                    arrMedicines.add(Medicine(freq, start_date, finish_date, name, condition))
                }

                notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        arrMedicinas = arrMedicines.toTypedArray<Medicine>()
        notifyDataSetChanged()

    }
    inner class RenglonMedicina(var vistaRenglon: View): RecyclerView.ViewHolder(vistaRenglon)
    {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RenglonMedicina {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.renglon_medicina, parent, false)
        return RenglonMedicina(vista)
    }

    override fun getItemCount(): Int {
        return arrMedicinas?.size!!
    }


    override fun onBindViewHolder(holder: RenglonMedicina, position: Int) {
        val medicine = arrMedicinas?.get(position)
        holder.vistaRenglon.tvMedicina.text = medicine?.medicine
        holder.vistaRenglon.shortMedicina.text = medicine?.medicine?.take(1)
        holder.vistaRenglon.tvCondicion.text = medicine?.condition
        holder.vistaRenglon.tvInicio.text = medicine?.start_date
        holder.vistaRenglon.tvFin.text = medicine?.end_date

        //holder.vistaRenglon.tInputSintoma.toString()
        holder.vistaRenglon.setOnClickListener{
            listener?.itemClicked(position)
        }
    }
}