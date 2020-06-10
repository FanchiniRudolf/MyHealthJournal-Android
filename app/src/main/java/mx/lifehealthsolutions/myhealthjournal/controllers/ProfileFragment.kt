package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.emailUser
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.User

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

        if(auth != null){
            if(User.age == ""  && User.sex == ""  && User.height == "" && User.weight == "")
            User.downloadInfo(view)
        }

        view.btn_logout.setOnClickListener { view ->
            auth.signOut()
            val mainIntent = Intent(activity, MainActivity::class.java)
            startActivity(mainIntent)

        }
        view.btn_about.setOnClickListener { view ->
            auth.signOut()
            val aboutIntent = Intent(activity, AboutActivity::class.java)
            startActivity(aboutIntent)

        }

        view.btn_privacy.setOnClickListener { view ->
            Log.w("clicking on button:", "se hizo click para ver privacy")
            val url= Uri.parse("https://fanchinirudolf.github.io/MyHealthJournal-Android/Privacy-Policy.html")
            val intBrowser = Intent(Intent.ACTION_VIEW, url)

            startActivity(intBrowser)
        }

        view.nameUser.text = User.name
        view.emailUser.text = User.email
        view.ageUser.text = User.age
        view.genderUser.text = User.sex
        view.heightUser.text = User.height
        view.weightUser.text = User.weight


        return view
    }

}
