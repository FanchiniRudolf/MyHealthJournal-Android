package mx.lifehealthsolutions.myhealthjournal.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.activity_reminder.conditionSpinner
import kotlinx.android.synthetic.main.activity_reminder.finishDateTV
import kotlinx.android.synthetic.main.activity_reminder.frequencySpinner
import kotlinx.android.synthetic.main.activity_reminder.inputMedicine
import kotlinx.android.synthetic.main.activity_reminder.registerBtn
import kotlinx.android.synthetic.main.activity_reminder.startDateTV
import kotlinx.android.synthetic.main.fragment_reminder.*
import kotlinx.android.synthetic.main.fragment_reminder.view.*
import kotlinx.android.synthetic.main.fragment_reminder.view.conditionSpinner
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.DownloadedDataListener
import mx.lifehealthsolutions.myhealthjournal.models.NotificationUtils
import mx.lifehealthsolutions.myhealthjournal.models.User
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity(), DownloadedDataListener {
    lateinit var spinner: Spinner
    var cal = Calendar.getInstance()
    var currentTime = cal.time

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        spinner = conditionSpinner
        User.downloadConditionNames(this)

        btn_back.setOnClickListener{
            finish()
        }

        val dateSetListenerStart = object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewStart()
            }
        }

        startDateTV.setOnClickListener{view->
            DatePickerDialog(this, dateSetListenerStart,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        val dateSetListenerFinish = object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewFinish()
            }
        }
        finishDateTV.setOnClickListener{view->
            DatePickerDialog(this, dateSetListenerFinish,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        registerBtn.setOnClickListener { view ->
            saveChanges()
        }
    }

    private fun saveChanges() {
        mostrarMensaje()
    }

    private fun mostrarMensaje() {
        val alerta = AlertDialog.Builder(this)
        alerta.setMessage("¿Deseas agregar este tratamiento?")
            .setPositiveButton("Sí", DialogInterface.OnClickListener{
                    dialog, which ->
                registerMedicineDB()
                createAlarm()
                startDateTV.text = "--/--/----"
                finishDateTV.text = "--/--/----"
                inputMedicine.getText()?.clear()
                spinner.setSelection(0)
                frequencySpinner.setSelection(0)
            })
            .setNegativeButton("No", null)
            .setCancelable(false)
            .create()
        alerta.show()
    }
    private fun updateDateInViewStart() {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        startDateTV.text = sdf.format(cal.time)
    }

    private fun updateDateInViewFinish() {
        val myFormat = "MM-dd-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        finishDateTV.text = sdf.format(cal.time)
    }

    fun registerMedicineDB() {
        var startDate = startDateTV.text.toString()
        var finishDate = finishDateTV.text.toString()
        var medicine = inputMedicine.text.toString()
        var entryCondition = conditionSpinner.selectedItem
        var frequency = frequencySpinner.selectedItem.toString().take(1)

        var user = FirebaseAuth.getInstance().currentUser?.email
        if(user == ""){
            user = User.email
        }

        if (startDate != null && finishDate != null && entryCondition != null &&
            medicine != null && frequency != null) {
            val newEntry = hashMapOf(
                "treatment_start" to startDate,
                "treatment_finish" to finishDate,
                "condition" to entryCondition,
                "medicine" to medicine,
                "frequency" to frequency,
                "hour_of_entry" to currentTime
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("Users/$user/Conditions/$entryCondition/Medicines").document("$startDate-$medicine")
                .set(newEntry)

            db.collection("Users/$user/Conditions/$entryCondition/Medicines").document("$startDate-$medicine")
                .set(newEntry)
            db.collection("Users/$user/Medicines").document("$startDate-$medicine")
                .set(newEntry)
        }
    }
    override fun didFinishDownload(adapter: SpinnerAdapter) {
        Log.w("didFinishDownload", "********Ha entrado a didFinishDownload")
        print(adapter)
        spinner.adapter = adapter
        print(adapter)
        val numberConditions = spinner.count
        Log.w("onActivityResult", "El spinner tiene ${numberConditions} valores")
    }

    fun createAlarm(){
        var hour = Calendar.HOUR_OF_DAY
        var currentTime = cal.get(Calendar.HOUR_OF_DAY)
        var currentMinute = cal.get(Calendar.MINUTE)
        val position = frequencySpinner.selectedItemPosition
        val days = ArrayList<Int>()
        days.add(Calendar.SATURDAY)
        days.add(Calendar.SUNDAY)
        days.add(Calendar.MONDAY)
        days.add(Calendar.THURSDAY)
        days.add(Calendar.TUESDAY)
        days.add(Calendar.WEDNESDAY)
        days.add(Calendar.THURSDAY)
        days.add(Calendar.FRIDAY)
        when(position){
            0 ->if(currentTime+4<=24){
                hour = (currentTime+4)
            }else hour = (currentTime+4)%4
            1 -> if(currentTime+8<=24){
                hour = (currentTime+8)
            }else hour = (currentTime+8)%8
            2 -> if(currentTime+12<=24){
                hour = (currentTime+12)
            }else hour = (currentTime+12)
            3 -> hour = (currentTime+24)%24
        }
        var intent = Intent(AlarmClock.ACTION_SET_ALARM)
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour)
        intent.putExtra(AlarmClock.EXTRA_MINUTES, currentMinute)
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Hora de tomar tu medicina")
        intent.putExtra(AlarmClock.EXTRA_DAYS, days)
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        startActivity(intent)

    }


}