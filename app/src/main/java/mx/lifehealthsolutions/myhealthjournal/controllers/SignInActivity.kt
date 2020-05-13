package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.User

class SignInActivity : AppCompatActivity() {
    val RC_SIGN_IN = 123
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()
        val  gso =  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sign_in_button.setOnClickListener{
            val signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        };

        buttonIniciar.setOnClickListener{
            signIn()
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    fun signIn(){
        var email = semail.text.toString()
        var password = spassword.text.toString()
        if(email != null && password != null){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                    //Verificamos que la tarea se ejecutó correctamente
                        task ->
                    if (task.isSuccessful) {
                        // Si se inició correctamente la sesión vamos a la vista Home de la aplicación
                        val mainIntent = Intent(this, MainActivity::class.java)
                        startActivity(mainIntent)

                    } else {
                        // sino le avisamos el usuairo que orcurrio un problema
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    fun register(v: View){
        val regIntent = Intent(this, SignUpActivity::class.java)
        startActivity(regIntent)
    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val mainActivity = Intent(this, MainActivity::class.java)
                    startActivity(mainActivity)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.

                }

                // ...
            }
    }


    override fun onStart() {
        super.onStart()
        val  account = GoogleSignIn.getLastSignedInAccount(this);
    }
}
