package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_diary.view.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewCondition
import mx.lifehealthsolutions.myhealthjournal.models.Condition


/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment(), ListenerRecycler {
    var adaptadorCondition: AdapterViewCondition = AdapterViewCondition(FirebaseAuth.getInstance().currentUser?.email)
    lateinit var recyclerView: RecyclerView
    protected lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_diary, container, false)

        view.btnNewEvent.setOnClickListener { view ->
            val intent = Intent(activity, CreateEntryActiv::class.java)
            startActivity(intent)
        }
        recyclerView = view.recyclerEntradas


        val user = FirebaseAuth.getInstance().currentUser?.email

        adaptadorCondition = AdapterViewCondition(user)


        val layout = LinearLayoutManager(activity)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout

        adaptadorCondition?.listener =  this
        recyclerView.adapter =  adaptadorCondition
        // Return the fragment view/layout

        downloadConditions()
        adaptadorCondition?.conditions =  Condition.arrCondiciones
        adaptadorCondition?.notifyDataSetChanged()
        return view
    }

    fun downloadConditions() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser?.email
        var userStr =  "{${user}}"
        var arrConditions  =  mutableListOf<Condition>()
        db.collection("Users/$userStr/Conditions")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    var temp  = document.id
                    arrConditions.add(Condition(temp))

                }
                adaptadorCondition?.conditions =  arrConditions.toTypedArray()
                adaptadorCondition?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    override fun itemClicked(position: Int) {

    }
}
