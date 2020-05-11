package mx.lifehealthsolutions.myhealthjournal.models

import java.util.*
import kotlin.collections.ArrayList


class Condition(val name: String) {

    val entry_values =  ArrayList<Entry>()
    val severity_values = ArrayList<String>() //todo remove list is already on enrty

    companion object {
        val arrCondiciones = arrayOf(
            Condition("Asma"),
            Condition("Migraña"),
            Condition("COVID-19")
        )
    }

    fun graph(){
        //TODO add later
    }

    fun add_severity(description: String){
        severity_values.add(description)
    }

    fun add_entry(entry: Entry){
        entry_values.add(entry)
    }

    fun remove_severity(index: Int){
        severity_values.removeAt(index)
    }

    fun remove_entry(index: Int){
        entry_values.removeAt(index)
    }

    fun filter(filter: FilterType){
        //Todo make different comparators
    }
}