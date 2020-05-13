package mx.lifehealthsolutions.myhealthjournal.controllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
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

        // spinner condition
        var arr = ArrayList<String>() //todo irving
        arr.add("Asma")
        arr.add("Sars")
        arr.add("Covid")
        arr.add("asdfasdf")

        spinner = view.conditionSpinner
        var adapter = User.downloadConditionNames(this.requireActivity())
        spinner.adapter = adapter
        return view
    }

    // ---

    fun registerMedicineDB(v: View) {

    }

}
