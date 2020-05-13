package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
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
