package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about.*
import mx.lifehealthsolutions.myhealthjournal.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        btn_back2.setOnClickListener{
            finish()
        }

    }
}