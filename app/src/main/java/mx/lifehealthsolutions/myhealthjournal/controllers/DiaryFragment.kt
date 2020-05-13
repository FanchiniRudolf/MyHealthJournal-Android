package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_diary.view.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewEntry
import mx.lifehealthsolutions.myhealthjournal.models.Entry


/**
 * A simple [Fragment] subclass.
 */
class DiaryFragment : Fragment(), ListenerRecycler {
    var adaptadorEntrada: AdapterViewEntry = AdapterViewEntry(Entry.arrEntradas)
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


        val user = FirebaseAuth.getInstance().currentUser
            user?.email?.let { getEntries(it) }

        adaptadorEntrada = AdapterViewEntry(Entry.arrEntradas)
        val layout = LinearLayoutManager(activity)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layout

        adaptadorEntrada?.listener =  this
        recyclerView.adapter =  adaptadorEntrada
        // Return the fragment view/layout
        return view
    }

    fun getEntries(email: String){
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser?.email
        var userStr =  "{${user}}"
        var arrEntradas  =  ArrayList<Entry>()
        var userRef = db.collection("Users/{$user}/Entries")
            .get()
            .addOnSuccessListener { documents ->
                var entries_string = ArrayList<String>()
                for (document in documents) {
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                    entries_string.add(document.id)
                }
            }
            .addOnFailureListener { exception ->
                var exceptionString = ArrayList<String>()
                exceptionString.add("No entries found")
                var adapter = ArrayAdapter(this.requireActivity(), android.R.layout.simple_spinner_item, exceptionString) as SpinnerAdapter
            }
    }

    override fun itemClicked(position: Int) {

    }
}
