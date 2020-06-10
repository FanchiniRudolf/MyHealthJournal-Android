package mx.lifehealthsolutions.myhealthjournal.models

import java.util.*
import kotlin.collections.ArrayList


class Condition(val name: String, val description: String) {

    val entry_values =  ArrayList<Entry>()

    companion object {
        val arrCondiciones = arrayOf(
            Condition("Asma", "asdaasd"),
            Condition("Migraña", "dsadas"),
            Condition("COVID-19", "asdas")
        )
    }


    fun add_entry(entry: Entry){
        entry_values.add(entry)
    }

    fun remove_entry(index: Int){
        entry_values.removeAt(index)
    }

}