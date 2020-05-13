package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_startup_setup.*
import mx.lifehealthsolutions.myhealthjournal.R


class StartupSetup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup_setup)
    }

    private fun getGender(): String{
        val radioButtonID: Int = genderGroup.getCheckedRadioButtonId()
        val radioButton: View = genderGroup.findViewById(radioButtonID)
        val idx: Int = genderGroup.indexOfChild(radioButton)
        val r = genderGroup.getChildAt(idx) as RadioButton
        val gender = r.text.toString()

        return gender
    }

    fun registerUserDB(v: View) {
        var usrName = name?.text.toString()
        var usrAge = age?.text.toString()
        var usrWeight = weight?.text.toString()
        var usrHeight = height?.text.toString()
        var email = intent.getStringExtra("email")
        var usrGender = getGender()
        var usrCondition = txtCondition?.text.toString()
        var conditionDescription = txtCondition?.text.toString()

        if (usrName != null && usrAge != null && usrWeight != null && usrHeight != null && usrGender != null && usrCondition != null && conditionDescription != null) {
            val user = hashMapOf(
                "name" to usrName,
                "gender" to usrGender,
                "age" to usrAge,
                "weight" to usrWeight,
                "height" to usrHeight
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("Users").document(email)
                .set(user)
            db.collection("Users/$email/Conditions").document(usrCondition)
                .set("description" to conditionDescription)
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

    }
}
