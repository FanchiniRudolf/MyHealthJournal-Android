package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mx.lifehealthsolutions.myhealthjournal.R

class CreateEntryActiv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_entrada)
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
