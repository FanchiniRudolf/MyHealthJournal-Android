package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.User
import java.util.*

class CreateEntryActiv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_entrada)
        val spinner: Spinner = findViewById(R.id.spinnerTipo)
        val adapter = User.downloadConditionNames(this)
        spinnerTipo.adapter = adapter
        val thisMoment = Date()
        val todayDate = "${thisMoment.year+1900}-${thisMoment.month}-${thisMoment.date}"
        val currentTime = "${thisMoment.hours}:${thisMoment.minutes}"
        etFecha.setText(todayDate)
        etHora.setText(currentTime)
    }


    override fun onResume() {
        super.onResume()
        Log.w("idk", "Here we are")

        // spinner i think
        val spinner: Spinner = findViewById(R.id.spinnerTipo)
        val adapter = User.downloadConditionNames(this)
        spinnerTipo.adapter = adapter
        //TODO spinnerTipo.selectedItem =
    }


    fun registerEntryDB() {
        var entryDate = etFecha.text.toString()
        var entryTime = etHora.text.toString()
        var entryCondition = spinnerTipo.selectedItem
        var entryDescription = etDescripcion.text.toString()
        var entrySeverity = spinnerSeveridad.selectedItemPosition
        var entryEventTime = spinnerTiempoEvento.selectedItemPosition

        val user = FirebaseAuth.getInstance().currentUser?.email

        if (entryDate != null && entryTime != null && entryCondition != null &&
            entryDescription != null && entrySeverity != null && entryEventTime != null) {
            val newEntry = hashMapOf(
                "date" to entryDate,
                "time" to entryTime,
                "condition" to entryCondition,
                "description" to entryDescription,
                "severity" to entrySeverity,
                "event-time" to entryEventTime
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("Users/$user/Conditions/$entryCondition/Entries").document("$entryDate-$entryTime")
                .set(newEntry)
        }
    }


    fun createNewCondition(v: View) {
        val newCondIntent = Intent(this, CreateConditonActiv::class.java)
        startActivity(newCondIntent)
    }


    fun exitSavingData(v: View) {
        registerEntryDB()
        finish()
    }


    private fun mostrarMensaje() {
        val alerta = AlertDialog.Builder(this)
        alerta.setMessage("¿Realmente deseas salir?\nNo se guardará la entrada.")
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
