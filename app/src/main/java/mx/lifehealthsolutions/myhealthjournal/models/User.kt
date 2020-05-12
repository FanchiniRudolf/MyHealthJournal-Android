package mx.lifehealthsolutions.myhealthjournal.models

import android.content.ContentValues.TAG
import android.util.Log
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

    fun download(){
        //download from the cloud
        db.collection("Users/{$email}")
            .whereEqualTo("email", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
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