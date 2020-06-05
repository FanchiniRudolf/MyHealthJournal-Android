package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import mx.lifehealthsolutions.myhealthjournal.R

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
    }


    fun setAir(data: String){
        if (textView15 != null){
            textView15.setText(data)
        }else{
            println("******************************************************")
            println("No text view 15")
            println("******************************************************")
        }

    }

    fun setClimate(data: String){
        if (textView17 != null){
            textView17.setText(data)
        }else{
            println("******************************************************")
            println("No text view 17")
            println("******************************************************")
        }

    }

    fun setUV(data: String){
        if (textView19 != null){
            textView19.setText(data)
        }else{
            println("******************************************************")
            println("No text view 17")
            println("******************************************************")
        }

    }

}
