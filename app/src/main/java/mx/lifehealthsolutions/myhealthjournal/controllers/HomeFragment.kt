package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.User

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    protected lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)

        view.fab_new_entry.setOnClickListener { view ->
            val intentNewEntry = Intent(activity, CreateEntryActiv::class.java)
            startActivity(intentNewEntry)
        }

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onStart() {
            super.onStart()
            //configureGPS()
        if(User.nombre != "undefined"){
            setWelcomeMessage("${User.nombre}")
        }
        if(tvHomeTitle.text  == "Hola de nuevo"){
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("Users/").document("${FirebaseAuth.getInstance().currentUser?.email}")
            userRef.get()
                .addOnSuccessListener { document ->
                    if(document.data != null){
                        setWelcomeMessage(document.data!!.get("name") as String)
                    }
                }
        }
    }

    fun setWelcomeMessage(data:String){
        tvHomeTitle.text  = "¡Hola de nuevo, $data!"
    }
    fun setAir(data: String){


    }

    fun setClimate(data: String){
        if (temperature != null){
            temperature.setText(data)
        }else{
            println("******************************************************")
            println("No text view 17")
            println("******************************************************")
        }

    }

    fun setUV(data: String){


    }

}
