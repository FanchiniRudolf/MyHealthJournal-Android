package mx.lifehealthsolutions.myhealthjournal.controllers

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.Condition
import mx.lifehealthsolutions.myhealthjournal.models.User
import org.json.JSONObject


class MainActivity : AppCompatActivity(), LocationListener {

    private val GPS_PERMIT: Int = 200
    private lateinit var gps: LocationManager
    private lateinit var position: Location
    private lateinit var fragHome: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidNetworking.initialize(this)




        fragHome = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorFragmentos, fragHome)
            .commit()

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            val signinIntent = Intent(this, SignInActivity::class.java)
            startActivity(signinIntent)
            finish()
        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorFragmentos, fragHome)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .addToBackStack(null)
                        .commit()
                    setClimate()
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
                    println("Impossible")
            }
            

            true
        }

        val test_user = User
        test_user.conditions_list.add(Condition("asma"))
    }

    override fun onStart() {
        super.onStart()
        configureGPS()

    }

    override fun onResume() {
        super.onResume()
        //Check for permit
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED){
            //have permit allowed
            gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1000f, this)
            onLocationChanged(gps.getLastKnownLocation(LocationManager.GPS_PROVIDER))
        } else{
            //ask for permit
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), GPS_PERMIT)
        }

    }

    override fun onPause() {
        super.onPause()
        gps.removeUpdates(this)
    }


    private fun setClimate() {
        val latitud = position.latitude
        val longitude = position.longitude
        val address = "https://api.waqi.info/feed/geo:$latitud;$longitude/?token=c77ba1b4e5441ffaaec07a8cb58efc3307bdf850"
        AndroidNetworking.get(address)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    println(response)
                    val  aqi = response?.getJSONObject("data")?.getString("aqi")
                    val pm10 = response?.getJSONObject("data")?.getJSONObject("iaqi")?.getJSONObject("pm10")?.getString("v")
                    println(aqi)
                    println(pm10)
                    //textView15.setText("pmo10: $pmo10, aqi: $aqi")
                    fragHome.setData("pm10: $pm10, aqi: $aqi")

                }

                override fun onError(anError: ANError?) {
                    println("******************************************************")
                    println("error")
                    println("******************************************************")
                    fragHome.setData("pmo10: NAN, aqi: NAN")
                }

            })
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

    override fun onLocationChanged(location: Location?) {
        if(location != null){
            position = location
            setClimate()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == GPS_PERMIT && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Granted permit
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                    //have permit allowed
                    gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0f, this)
                    onLocationChanged(gps.getLastKnownLocation(LocationManager.GPS_PROVIDER))
                }
            } else {
                //Show Message on how to do it
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

}
