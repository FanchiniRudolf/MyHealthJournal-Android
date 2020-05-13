package mx.lifehealthsolutions.myhealthjournal.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import kotlinx.android.synthetic.main.fragment_reminder.*
import kotlinx.android.synthetic.main.fragment_reminder.view.*
import kotlinx.android.synthetic.main.fragment_reminder.view.conditionSpinner
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.Condition
import mx.lifehealthsolutions.myhealthjournal.models.User

/**
 * A simple [Fragment] subclass.
 */
class ReminderFragment : Fragment() {

    lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_reminder, container, false)

        spinner = view.conditionSpinner
        var adapter = User.downloadConditionNames(this.requireActivity())
        spinner.adapter = adapter
        return view
    }

    // ---

    fun registerMedicineDB(v: View) {
        var startDate = startDateET.text.toString()
        var finishDate = finishDate.text.toString()
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
                "medicine" to medicine
            )
            val db = FirebaseFirestore.getInstance()
            db.collection("Users/$user/Conditions/$entryCondition/Medicines").document("$startDate-$medicine")
                .set(newEntry)
        }
    }

}
