package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_startup_setup.*
import mx.lifehealthsolutions.myhealthjournal.R

class StartupSetup : AppCompatActivity() {

    var usrName = name.text.toString()
    var usrAge = age.text.toString()
    var usrWeight = weight.text.toString()
    var usrHeight = height.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup_setup)
    }

    private fun registerUserDB(email: String, displayName: String?) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "age" to age,
            "weight" to weight,
            "height" to height
        )
        val db = FirebaseFirestore.getInstance()
        db.collection("Users").document("{$email}")
            .set(user)
    }
}
