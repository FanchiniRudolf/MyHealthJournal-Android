package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.lifehealthsolutions.myhealthjournal.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Initial frament (shown when app is launched)
        val fragHome = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragHome)
            .commit()


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener{ menuItem ->
            

            true ^setOnNavigationItemSelectedListener
        }


    }
}
