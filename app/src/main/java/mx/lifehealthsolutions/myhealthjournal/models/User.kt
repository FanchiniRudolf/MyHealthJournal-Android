package mx.lifehealthsolutions.myhealthjournal.models

import android.R
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.google.firebase.firestore.FirebaseFirestore

object User: Comparable<User> {

    val conditions_list =  ArrayList<Condition>()
    val medicine_list = ArrayList<Medicine>()
    lateinit var nombre: String
    lateinit var email: String
    lateinit var password: String
    var db = FirebaseFirestore.getInstance()

    override fun compareTo(other: User): Int {
        return nombre.compareTo(other.nombre)
    }

    fun upload(){
        //upload to the cloud

    }

    fun download(email: String,context: Context){
        //download from the cloud
        this.email = email
        db.collection("Users/$email/Conditions")
            .get()
            .addOnSuccessListener { documents ->
                var conditions_string = ArrayList<String>()
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    conditions_string.add(document.id)
                }
                var adapter = ArrayAdapter(context, R.layout.simple_spinner_item, conditions_string) as SpinnerAdapter
            }
            .addOnFailureListener { exception ->
                var exceptionString = ArrayList<String>()
                exceptionString.add("No conditions found")
                var adapter = ArrayAdapter(context, R.layout.simple_spinner_item, exceptionString) as SpinnerAdapter
            }
    }

    fun delete(){
        //todo make a function that deletes from firebase
    }

    fun addCondition(condition: Condition){
        conditions_list.add(condition)
    }

    fun removeCondition(condition: Condition){
        conditions_list.remove(condition)
    }

    fun addMedicine(medicine: Medicine){
        medicine_list.add(medicine)
    }

    fun removeMedicine(medicine: Medicine){
        medicine_list.remove(medicine)
    }
}