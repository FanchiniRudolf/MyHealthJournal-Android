package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.User

class EntryDataActiv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_data)
        val spinner:Spinner = findViewById(R.id.spinnerTipo)
        val adapter = User.downloadConditionNames(this)
        spinnerTipo.adapter = adapter
    }
}
