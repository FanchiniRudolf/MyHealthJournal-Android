package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_condition.*
import kotlinx.android.synthetic.main.activity_condition.view.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewEntry
import mx.lifehealthsolutions.myhealthjournal.models.Entry
import kotlin.collections.ArrayList

class ConditionActivity : AppCompatActivity(), ListenerRecycler {

    var entries =  ArrayList<Entry>()
    var adapterEntries: AdapterViewEntry = AdapterViewEntry(entries)
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)

        val condition_name = intent.getStringExtra("CONDITION")
        tvConditionName.text = condition_name


        downloadEntries(condition_name)
        createRecycler()
        createChart()

    }

    private fun createChart() {
        var  chart: LineChart = lcGraph
        //todo
    }

    private fun createRecycler() {

        adapterEntries?.listener = this


        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL

        val lineDivisor = DividerItemDecoration(this, layout.orientation)

        recyclerView = recyclerEntries
        recyclerView.layoutManager = layout
        recyclerView.adapter = adapterEntries
        recyclerView.addItemDecoration(lineDivisor)

    }

    fun downloadEntries(condition_name: String){
        val db = FirebaseFirestore.getInstance()
        var userStr =  FirebaseAuth.getInstance().getCurrentUser()?.email
        val conditions = db.collection("Users/$userStr/Conditions/")
        val conditionRef =  db.collection("Users/$userStr/Conditions/").document("$condition_name")

        conditionRef.get()
            .addOnSuccessListener { document ->
                if (document != null){
                    //print(typeOf(document.data))


                    db.collection("Users/$userStr/Conditions/$condition_name/Entries")
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                print("-------")
                                print("$document.data")
                                entries.add(Entry(condition_name, document.data.get("date") as String,
                                    document.data.get("severity") as Long,
                                    document.data.get("description") as String,
                                    document.data.get("event-time") as Long
                                ))
                            }
                            print("--------------")
                            Log.d("entries", "TAMAÑO: ${entries.size}")
                            print(entries.toString())
                            updateRecycler()
                        }

                }
                else{
                    //Empty Recycler
                    print("none")
                }
            }

    }

    private fun updateRecycler() {
        adapterEntries?.arrEntradas = entries
        adapterEntries?.notifyDataSetChanged()
    }

    override fun itemClicked(position: Int) {
        //TODO go to details of entry screen use put extras
    }
}
