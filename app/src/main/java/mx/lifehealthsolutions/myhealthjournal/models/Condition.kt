package mx.lifehealthsolutions.myhealthjournal.models

import java.util.*
import kotlin.collections.ArrayList


class Condition(val name: String) {

    val entry_values =  ArrayList<Entry>()

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

    fun add_entry(entry: Entry){
        entry_values.add(entry)
    }

    fun remove_entry(index: Int){
        entry_values.removeAt(index)
    }

    fun filter(filter: FilterType){
        //Todo make different comparators
    }
}