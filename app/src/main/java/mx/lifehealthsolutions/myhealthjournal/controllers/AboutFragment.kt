package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_about.view.*
import mx.lifehealthsolutions.myhealthjournal.R

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {
    protected lateinit var rootView: View
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_about, container, false)
        auth = FirebaseAuth.getInstance()

        view.btn_logout.setOnClickListener { view ->
            auth.signOut()
            val mainIntent = Intent(activity, MainActivity::class.java)
            startActivity(mainIntent)

        }
        return view
    }

    /*
    fun signOut(v: View){
        var email = semail.text.toString()
        var password = spassword.text.toString()
        if(email != null && password != null){
            auth.signOut()
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()

        }
    }
    */
}
