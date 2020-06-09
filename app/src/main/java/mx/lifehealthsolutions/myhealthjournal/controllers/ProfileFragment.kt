package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*
import mx.lifehealthsolutions.myhealthjournal.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {
    protected lateinit var rootView: View
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)
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
