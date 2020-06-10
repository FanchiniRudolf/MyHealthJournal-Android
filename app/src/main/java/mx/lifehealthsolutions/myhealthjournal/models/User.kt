package mx.lifehealthsolutions.myhealthjournal.models

import android.R
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.view.*
import mx.lifehealthsolutions.myhealthjournal.interfaces.DownloadedDataListener

object User: Comparable<User> {

    val conditions_list =  ArrayList<Condition>()
    val medicine_list = ArrayList<Medicine>()
    var name: String
    var email: String
    lateinit var password: String
    var sex: String
    var age: String
    var height: String
    var weight: String


    var db = FirebaseFirestore.getInstance()

    override fun compareTo(other: User): Int {
        return name.compareTo(other.name)
    }

    fun upload(){
        //upload to the cloud

    }
    init {
        name = ""
        sex = ""
        age = ""
        email = ""
        height = ""
        weight = ""
    }

    fun downloadConditionNames(context: Context) {
        //download from the cloud
        Log.w("download", "********entra al metodo de descarga")
        var user = FirebaseAuth.getInstance().currentUser?.email
        if(user == null) {
            user = User.email
        }
        var adapter: SpinnerAdapter
        var conditions_string = ArrayList<String>()
        conditions_string.add("Condiciones")

        // leer de la db
        db.collection("Users/$user/Conditions")
            .get()
            .addOnSuccessListener { documents ->
                // descarga de datos
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    conditions_string.add(document.id)
                }
                adapter = ArrayAdapter(context, R.layout.simple_spinner_item, conditions_string) as SpinnerAdapter
                //spinner.adapter = adapter
                val listener = context as DownloadedDataListener
                listener.didFinishDownload(adapter)


            }
            .addOnFailureListener { exception ->
                conditions_string.add("No se encontraron condiciones")
                adapter = ArrayAdapter(context, R.layout.simple_spinner_item, conditions_string) as SpinnerAdapter
                val listener = context as DownloadedDataListener
                listener.didFinishDownload(adapter)
            }
    }

    fun downloadInfo(view: View) {
        //download from the cloud
        var user = FirebaseAuth.getInstance().currentUser?.email
        if(user == null) {
            user = User.email
        }
        // leer de la db
        if(user != null){
            db.collection("Users/").document("$user")
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        name = document.data!!.get("name").toString()
                        email = document.id
                        sex = document.data!!.get("gender").toString()
                        age = document.data!!.get("age").toString()
                        height = document.data!!.get("height").toString()
                        weight = document.data!!.get("weight").toString()

                        view.emailUser.text = email
                        view.nameUser.text = User.name
                        view.genderUser.text = User.sex
                        view.ageUser.text = User.age
                        view.heightUser.text = User.height + " cms"
                        view.weightUser.text = User.weight +  " kgs"
                    }
                }
                .addOnFailureListener { exception ->
                    name = ""
                    sex = ""
                    age = ""
                    height = ""
                    weight = ""
                }
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