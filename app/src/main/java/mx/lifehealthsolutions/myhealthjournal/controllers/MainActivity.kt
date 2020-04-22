package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
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
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragmentos, fragHome)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit()
                }

                R.id.navigation_reminders -> {
                    val fragReminders = ReminderFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragmentos, fragReminders)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit()
                }

                R.id.navigation_diary -> {
                    val fragDiary = DiaryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragmentos, fragDiary)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit()
                }

                R.id.navigation_about -> {
                    val fragAbout = AboutFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragmentos, fragAbout)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit()
                }

                else ->
                    println("esto no deberia pasar salu2")
            }
            

            true
        }


    }
}
