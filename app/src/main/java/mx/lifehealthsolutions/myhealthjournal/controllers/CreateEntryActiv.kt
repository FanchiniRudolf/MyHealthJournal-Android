package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.User

class CreateEntryActiv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_entrada)
        val spinner: Spinner = findViewById(R.id.spinnerTipo)
        val adapter = User.downloadConditionNames(this)
        spinnerTipo.adapter = adapter

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




    fun exitSavingData(v: View) {
        println("******************************************************")
        println("salio guardando datos")
        println("******************************************************")
        registerEntryDB()
        finish()
    }

    fun exitWithoutSavingData(v: View) {
        println("******************************************************")
        println("salio SIN guardar datos")
        println("******************************************************")
        finish()
    }
}
