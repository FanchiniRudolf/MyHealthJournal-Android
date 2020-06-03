package mx.lifehealthsolutions.myhealthjournal.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_condition.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.Entry
import kotlin.collections.ArrayList

class ConditionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condition)
        val condition_name = intent.getStringExtra("CONDITION")

        tvConditionName.text = condition_name
        downloadEntries(condition_name)
        //todo
        /*
        updateListener()
        createChart()
        */
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
                    tvConditionDesc.text = document.data?.get("description")?.toString()
                    var entries =  ArrayList<Entry>()

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
                        }

                }
                else{
                    tvConditionDesc.text = "NO DATA FOUND"
                }
            }

    }
}
