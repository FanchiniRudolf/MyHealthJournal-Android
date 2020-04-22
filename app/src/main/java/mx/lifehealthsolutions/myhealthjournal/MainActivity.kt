package mx.lifehealthsolutions.myhealthjournal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


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
