package mx.lifehealthsolutions.myhealthjournal.controllers

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_condition.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.interfaces.ListenerRecycler
import mx.lifehealthsolutions.myhealthjournal.models.AdapterViewEntry
import mx.lifehealthsolutions.myhealthjournal.models.Entry
import com.github.mikephil.charting.data.Entry as ChartEntry
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.collections.ArrayList

class ConditionActivity : AppCompatActivity(), ListenerRecycler {

    private var entries =  ArrayList<Entry>()
    private var chartEntries =  ArrayList<ChartEntry>()
    private var chartEntryMinimum = Float.MAX_VALUE
    private var chartEntryMaximum = Float.MIN_VALUE
    private var adapterEntries: AdapterViewEntry = AdapterViewEntry(entries)
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)

        val condition_name = intent.getStringExtra("CONDITION")
        tvConditionName.text = condition_name


        downloadEntries(condition_name)
        createRecycler()


    }

    private fun createChart() {
        val  chart: LineChart = lcGraph


        //reduceData()
        Log.w("chartmax", (chartEntryMaximum-chartEntryMinimum).toString())
        Log.w("chartmax", (chartEntryMaximum).toString())
        Log.w("chartmini", (chartEntryMinimum).toString())
        Log.w("chart", chartEntries.toString())
        val data = LineDataSet(chartEntries, "Histórico")
        data.setDrawValues(true)
        data.color = Color.GREEN
        data.setDrawFilled(true)
        chart.data = LineData(data)
        data.fillColor = Color.GREEN
        chart.description.text = "Historico"
        chart.animateX(1000, Easing.EaseInCubic)
        chart.xAxis.axisMaximum = chartEntryMaximum+100
        chart.xAxis.axisMinimum = chartEntryMinimum-100
        //todo agrupar datos


    }

    private fun reduceData() {
        for (chartEntry in chartEntries){
            chartEntry.x = chartEntry.x - chartEntryMinimum
        }
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
                                addEntry(document.data.get("severity").toString().toFloat(), document.data.get("date")  as String, document.data.get("time") as String)
                            }
                            createChart()
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


    private fun addEntry(severity: Float, date: String, time: String) {
        val dateArr = date.split("-").toMutableList()
        if (dateArr[1].length == 1) dateArr[1] = "0${dateArr[1]}"
        if (dateArr[2].length == 1) dateArr[2] = "0${dateArr[2]}"
        val dateTime = "${dateArr[0]}-${dateArr[1]}-${dateArr[2]}T${time}:00" //concatenation
        val date = LocalDateTime.parse(dateTime)
        val seconds = date.toEpochSecond(ZoneOffset.MIN).toString().toFloat()/60*60*24
        if (seconds < chartEntryMinimum) chartEntryMinimum = seconds
        if (seconds > chartEntryMaximum) chartEntryMaximum = seconds
        var entry = ChartEntry(seconds, severity)
        chartEntries.add(entry)
    }

    private fun updateRecycler() {
        adapterEntries?.arrEntradas = entries
        adapterEntries?.notifyDataSetChanged()
    }

    override fun itemClicked(position: Int) {
        //TODO go to details of entry screen use put extras
    }
}
