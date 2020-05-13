package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val todayDate = "${thisMoment.year+1900}/${thisMoment.month}/${thisMoment.date}"
        val currentTime = "${thisMoment.hours}:${thisMoment.minutes}"
        etFecha.setText(todayDate)
        etHora.setText(currentTime)


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

    /*
    just a reference, will delet this soon

    fun regresar(v: View) {
        // primero voy a suponer que es cero
        val sueldo = etSueldo.text.toString().toInt()
        val limiteCredito = when (tipoTarjeta) {
            1 -> (sueldo * 2.5).toInt()
            2 -> (sueldo * 5.2).toInt()
            else -> 0
        }

        // ya tengo el limite de credito calculado
        // Listos para regresar a la actividad anterior
        val intRegreso = Intent()
        intRegreso.putExtra("limiteCredito", limiteCredito)
        // ya voy a entregar datos
        // pide el codigo de regreso y los datos
        setResult(RESULT_OK, intRegreso)
        // y termina esta actividad
        finish()
    }
     */

    fun exitSavingData(v: View) {
        println("******************************************************")
        println("salio guardando datos")
        println("******************************************************")
        finish()
    }

    fun exitWithoutSavingData(v: View) {
        println("******************************************************")
        println("salio SIN guardar datos")
        println("******************************************************")
        finish()
    }
}
