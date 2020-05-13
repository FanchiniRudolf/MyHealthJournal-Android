package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import mx.lifehealthsolutions.myhealthjournal.R


class SignUpActivity : AppCompatActivity() {
    val RC_SIGN_IN = 123
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

    }

    fun addUser(v: View){
        var email = email.text.toString()
        var password = password.text.toString()
        var cpassword = cpassword.text.toString()
        var name = name.text.toString()
        var age = age.text.toString()
        var weight = weight.text.toString()


        if(cpassword != password){
            Toast.makeText(
                baseContext, " Passwords do not match.",
                Toast.LENGTH_SHORT
            ).show()
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        Toast.makeText(
                            baseContext, "Authentication success.",

                            Toast.LENGTH_SHORT
                        ).show()
                        val user = auth.currentUser
                        val  mainIntent = Intent(this, MainActivity::class.java)
                        registerUserDB(email, user?.displayName)
                        startActivity(mainIntent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // ...
                }
        }
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
