package mx.lifehealthsolutions.myhealthjournal.models

import java.util.Date


data class Medicine(val frequency: String, val start_date: String, val end_date: String, val medicine: String, val condition:String) {

    val entry_values =  ArrayList<Entry>()

    companion object {
        val arrMedicinas = arrayOf(
            Medicine("4", "06-09-2020",  "06-09-2020", "Paracetamol", "Migraña")
        )
    }


    fun add_entry(entry: Entry){
        entry_values.add(entry)
    }

    fun remove_entry(index: Int){
        entry_values.removeAt(index)
    }
}