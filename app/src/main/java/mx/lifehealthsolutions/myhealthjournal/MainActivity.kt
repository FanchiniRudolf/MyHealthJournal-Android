package mx.lifehealthsolutions.myhealthjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.lifehealthsolutions.myhealthjournal.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fragmento inicial
        val fragHome = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragHome)
            .commit()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

    }
}
