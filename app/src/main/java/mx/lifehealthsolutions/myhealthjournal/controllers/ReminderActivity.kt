package mx.lifehealthsolutions.myhealthjournal.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                startDateTV.text = "--/--/----"
                finishDateTV.text = "--/--/----"
                inputMedicine.getText()?.clear()
                spinner.setSelection(0)
                frequencySpinner.setSelection(0)
                createNotification(startDateTV.text.toString(), cal.time.toString())
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

    fun createNotification(date: String, hour: String){
        Log.w("createNotification", "Ha entrado a createNotification")
        val not = NotificationUtils()
        val time = "${Calendar.HOUR_OF_DAY}:${Calendar.MINUTE}"
        val tempDate = "10/06/2020"
        //not.setNotification(java.util.Calendar.getInstance().timeInMillis, time, date, this.requireActivity())
        not.setNotification(java.util.Calendar.getInstance().timeInMillis, time, tempDate, this)
    }


}