package mx.lifehealthsolutions.myhealthjournal.controllers

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import kotlinx.android.synthetic.main.fragment_reminder.*
import kotlinx.android.synthetic.main.fragment_reminder.view.*
import kotlinx.android.synthetic.main.fragment_reminder.view.conditionSpinner
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.DownloadedDataListener
import mx.lifehealthsolutions.myhealthjournal.models.Condition
import mx.lifehealthsolutions.myhealthjournal.models.User
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ReminderFragment : Fragment(), DownloadedDataListener {

    lateinit var spinner: Spinner
    var cal = Calendar.getInstance()
    var currentTime = cal.time

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_reminder, container, false)

        spinner = view.conditionSpinner
        var adapter = User.downloadConditionNames(this.requireActivity())
        // esto se manejará con un listener
        //spinner.adapter = adapter // se esta regresando un adaptador vacio !!
        //spinner.setSelection(1, false)

        val dateSetListenerStart = object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewStart()
            }
        }

        view.btnStart.setOnClickListener{view->
            DatePickerDialog(this.requireContext(), dateSetListenerStart,
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

        view.btnFinish.setOnClickListener{view->
            DatePickerDialog(this.requireContext(), dateSetListenerFinish,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        view.registerBtn.setOnClickListener { view ->
            saveChanges()
        }

        return view
    }


    override fun onResume() {
        super.onResume()
        Log.w("onResume", "Se ha llamado a onResume")
        User.downloadConditionNames(this.requireActivity())

    }

    private fun saveChanges() {
        mostrarMensaje()
    }

    private fun mostrarMensaje() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setMessage("¿Deseas guardar los cambios hechos?")
            .setPositiveButton("Sí", DialogInterface.OnClickListener{
                    dialog, which ->
                registerMedicineDB()
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

    // ---

    fun registerMedicineDB() {
        var startDate = startDateTV.text.toString()
        var finishDate = finishDateTV.text.toString()
        var medicine = inputMedicine.text.toString()
        var entryCondition = conditionSpinner.selectedItem
        var frequency = frequencySpinner.selectedItem

        val user = FirebaseAuth.getInstance().currentUser?.email

        if (startDate != null && finishDate != null && entryCondition != null &&
            medicine != null && frequency != null) {
            val newEntry = hashMapOf(
                "treatment_start" to startDate,
                "treatment_finish" to finishDate,
                "condition" to entryCondition,
                "medicine" to medicine,
                "hour_of_entry" to currentTime
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("Users/$user/Conditions/$entryCondition/Medicines").document("$startDate-$medicine")
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

}
