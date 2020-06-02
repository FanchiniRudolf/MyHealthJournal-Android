package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_condition.*
import mx.lifehealthsolutions.myhealthjournal.R

class ConditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)
        val condition_name = intent.getStringExtra("CONDITION")

        tvConditionName.text = condition_name
        //todo
        /*downloadEntries()
        updateListener()
        createChart()
        */
    }
}
