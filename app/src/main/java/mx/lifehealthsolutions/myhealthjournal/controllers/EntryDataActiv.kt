package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
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
                    db.collection("Users/$userStr/Conditions/$condition_name/Entries")
                        .document("$entry_name")
                        .get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                print("-------")
                                print("$document.data")
                                Log.w("document", "$document.doc")
                                //entry = Entry()
                             }
                            setData()
                        }
                }
                else{
                    //Empty Recycler
                    print("none")
                }
            }

    }

    private fun setData() {
        //todo
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
