package mx.lifehealthsolutions.myhealthjournal.controllers

import android.app.Activity
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
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.DownloadedDataListener
import mx.lifehealthsolutions.myhealthjournal.models.User
import java.time.LocalDateTime
import java.util.*

class CreateEntryActiv : AppCompatActivity(), DownloadedDataListener {

    private lateinit var spinner: Spinner
    private var did_return_from_activity = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("onCreate", "********Se ha llamado a onCreate")
        setContentView(R.layout.activity_crear_entrada)
        spinner = findViewById(R.id.spinnerTipo)
        //val adapter = User.downloadConditionNames(this, spinner)
        //spinnerTipo.adapter = adapter
        val thisMoment = LocalDateTime.now()
        val todayDate = "${thisMoment.year}-${thisMoment.monthValue}-${thisMoment.dayOfMonth}"
        val currentTime = "${thisMoment.hour}:${thisMoment.minute}"
        etFecha.setText(todayDate)
        etHora.setText(currentTime)

        btn_back.setOnClickListener{
            if (spinner.selectedItemPosition != 0 && etDescripcion.text.toString().isNotEmpty()
                && etFecha.text.toString().isNotEmpty() && etHora.text.toString().isNotEmpty()) {
                alertForExit()
            } else {
                finish()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Log.w("onResume", "Se ha llamado a onResume")
        User.downloadConditionNames(this)
    }


    fun registerEntryDB() {
        var entryDate = etFecha.text.toString()
        entryDate = entryDate.replace('/', '-')
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


    private val CREATE_CONDITION_CODE = 600
    fun createNewCondition(v: View) {
        val newCondIntent = Intent(this, CreateConditonActiv::class.java)
        startActivityForResult(newCondIntent, CREATE_CONDITION_CODE)
    }


    // se activa cuadno la segunda actividad diga ya termine y este regresando un resultado
    // sabemos que regreso de otra actividad
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_CONDITION_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.w("resultCode", "Result code is RESULT_OK")
                if (data != null) {
                    // el -1 se regresa si no se encuentra la llave
                    //val lastIndex = adapter
                    // NO poner lo de que ponga le ultimo
                    did_return_from_activity = true
                }
            }
        }
    }


    fun exitSavingData(v: View) {
        if (spinner.selectedItemPosition != 0 && etDescripcion.text.toString().isNotEmpty()
            && etFecha.text.toString().isNotEmpty() && etHora.text.toString().isNotEmpty()) {
            registerEntryDB()
            finish()
        } else {
            alertForMissingFields()
        }
    }


    private fun alertForMissingFields() {
        val alerta = AlertDialog.Builder(this)
        alerta.setMessage("Error:\nFaltan campos por llenar.")
            .setPositiveButton("Entendido", null)
            .setCancelable(false)
            .create()
        alerta.show()
    }


    private fun alertForExit() {
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
        alertForExit()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            alertForExit()
            //moveTaskToBack(false);
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun didFinishDownload(adapter: SpinnerAdapter) {
        // la funcion ha terminado y está regresando el adapter
        Log.w("CreateEntryActiv", "********se ha llamado a didFinishDownload")
        print(adapter)
        spinner.adapter = adapter
        print(adapter)
        if (did_return_from_activity) {
            val numberConditions = spinner.count
            val lastIndex = numberConditions - 1
            spinner.setSelection(lastIndex, false)
            Log.w("onActivityResult", "El spinner tiene ${numberConditions} valores")
        }

    }
}
