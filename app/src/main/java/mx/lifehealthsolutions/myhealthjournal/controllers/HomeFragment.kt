package mx.lifehealthsolutions.myhealthjournal.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import mx.lifehealthsolutions.myhealthjournal.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
            super.onStart()
            //configureGPS()
    }


    fun setData(data: String){
        if (textView15 != null){
            textView15.setText(data)
        }else{
            println("******************************************************")
            println("No text view 15")
            println("******************************************************")
        }

    }

}
