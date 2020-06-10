package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_diary.view.*
import kotlinx.android.synthetic.main.fragment_medicine.*
import kotlinx.android.synthetic.main.fragment_medicine.view.*
import kotlinx.android.synthetic.main.fragment_medicine.view.addTreatment
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.*


class MedicineFragment : Fragment(), ListenerRecycler {
    var adaptadorMedicine: AdapterViewMedicine = AdapterViewMedicine(FirebaseAuth.getInstance().currentUser?.email)
    lateinit var recyclerView: RecyclerView
    protected lateinit var rootView: View
    var arrMedicines  =  mutableListOf<Medicine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_medicine, container, false)


        view.addTreatment.setOnClickListener{
            val intentNewTreatment = Intent(activity, ReminderActivity::class.java)
            startActivity(intentNewTreatment)
        }

        val user = FirebaseAuth.getInstance().currentUser?.email
        if(user == ""){
            adaptadorMedicine = AdapterViewMedicine(user)
        }
        else{
            adaptadorMedicine = AdapterViewMedicine(User.email)

        }
        val layout = LinearLayoutManager(activity)
        layout.orientation = LinearLayoutManager.VERTICAL

        recyclerView = view.recyclerMedicinas
        recyclerView.layoutManager = layout
        adaptadorMedicine?.listener =  this
        recyclerView.adapter =  adaptadorMedicine
        // Return the fragment view/layout

        adaptadorMedicine?.notifyDataSetChanged()
        return view

    }

    override fun itemClicked(position: Int) {
        return
    }

    override fun onResume() {
        super.onResume()
        val db = FirebaseFirestore.getInstance()
        var userStr =  "${adaptadorMedicine.email}"
        arrMedicines.clear()
        db.collection("Users/$userStr/Medicines/")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    val freq = document.data.get("frequency").toString()
                    val start_date = document.data.get("treatment_start").toString()
                    val finish_date = document.data.get("treatment_finish").toString()
                    val name = document.data.get("medicine").toString()
                    val condition = document.data.get("condition").toString()
                    arrMedicines.add(Medicine(freq, start_date, finish_date, name, condition))
                }
                adaptadorMedicine.arrMedicinas = arrMedicines.toTypedArray<Medicine>()
                Log.i("TAMAÑO", adaptadorMedicine.arrMedicinas!!.size.toString())
                adaptadorMedicine.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

    }

}