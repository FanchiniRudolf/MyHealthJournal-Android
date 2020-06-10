package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crear_entrada.*
import mx.lifehealthsolutions.myhealthjournal.R
import mx.lifehealthsolutions.myhealthjournal.models.Entry
import mx.lifehealthsolutions.myhealthjournal.models.User

class EntryDataActiv : AppCompatActivity() {

    private lateinit var entry: Entry
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_data)
        Log.w("Entry Name", intent.getStringExtra("ENTRY"))
        Log.w("Condition Name", intent.getStringExtra("CONDITION"))
        val entry_name = intent.getStringExtra("ENTRY")
        val condition_name = intent.getStringExtra("CONDITION")
        downloadEntries(condition_name, entry_name)

    }

    fun downloadEntries(condition_name: String, entry_name: String){
        val db = FirebaseFirestore.getInstance()
        var userStr =  FirebaseAuth.getInstance().getCurrentUser()?.email
        val conditionRef =  db.collection("Users/$userStr/Conditions/$condition_name/Entries").document("$entry_name")

        conditionRef.get()
            .addOnSuccessListener { document ->
                if (document != null){
                    //print(typeOf(document.data))


                    db.collection("Users/$userStr/Conditions/$condition_name/Entries/$entry_name")
                        .get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                print("-------")
                                print("$document.data")
                                entries.add(
                                    Entry(condition_name, document.data.get("date") as String,
                                    document.data.get("time") as String,
                                    document.data.get("severity") as Long,
                                    document.data.get("description") as String,
                                    document.data.get("event-time") as Long
                                )
                                )
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

}
