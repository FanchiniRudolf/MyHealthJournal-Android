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
