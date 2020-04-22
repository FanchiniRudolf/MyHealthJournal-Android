package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import com.androidnetworking.AndroidNetworking
import com.google.android.material.bottomnavigation.BottomNavigationView
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.Condition
import mx.lifehealthsolutions.myhealthjournal.models.User



class MainActivity : AppCompatActivity() {

    private val GPS_PERMIT: Int = 200
    private lateinit var gps: LocationManager
    private lateinit var position: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(this)
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

        val test_user = User("John Doe", "johndoe@gmail.com", "1234abcd")
        test_user.conditions_list.add(Condition("asma"))
    }

    override fun onStart() {
        super.onStart()
        configureGPS()
    }

    private fun configureGPS() {
        //create sensor gps admin
        gps = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(!gps.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //open android settings
            turnOnGPS()
        }
    }

    private fun turnOnGPS() {
        //user must turn it on app cannot do it
        val dialoge = AlertDialog.Builder(this)
        dialoge.setMessage("GPS is turned off, would you like to turn it on")
            .setCancelable(false)
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
            .setNegativeButton("No", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
        val alert = dialoge.create()
        alert.show()
    }

}
