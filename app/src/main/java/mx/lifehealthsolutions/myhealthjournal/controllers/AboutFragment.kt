package mx.lifehealthsolutions.myhealthjournal.controllers

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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

        view.btn_privacy.setOnClickListener { view ->
            val url= Uri.parse("https://fanchinirudolf.github.io/MyHealthJournal/Privacy-Policy.html")
            val intBrowser = Intent(Intent.ACTION_VIEW, url)

            startActivity(intBrowser)

        }

        return view
    }

}
