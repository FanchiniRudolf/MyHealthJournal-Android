package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_conditon.*
import mx.lifehealthsolutions.myhealthjournal.R

class CreateConditonActiv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_conditon)
    }


    private fun registerConditionDB() {
        var newCondition = txtNewCondition?.text.toString()
        var newConditionDescription = newCondDescription?.text.toString()

        val user = FirebaseAuth.getInstance().currentUser?.email

        if (newCondition != null && newConditionDescription != null) {
            val newCond = hashMapOf(
                //"condition" to newCondition,
                "description" to newConditionDescription
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("Users/$user/Conditions").document("$newCondition")
                .set(newCond)
        }
    }


    fun exitSavingData(v: View) {
        registerConditionDB()
        finish()
    }


    private fun mostrarMensaje() {
        val alerta = AlertDialog.Builder(this)
        alerta.setMessage("¿Realmente deseas salir?\nNo se guardará la condición.")
            .setPositiveButton("Sí", DialogInterface.OnClickListener{
                    dialog, which ->
                finish()
            })
            .setNegativeButton("No", null)
            .setCancelable(false)
            .create()
        alerta.show()
    }


    fun exitWithoutSavingData(v: View) {
        mostrarMensaje()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mostrarMensaje()
            //moveTaskToBack(false);
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}