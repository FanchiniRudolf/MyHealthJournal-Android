package mx.lifehealthsolutions.myhealthjournal.models

import mx.lifehealthsolutions.myhealthjournal.controllers.EventTime
import java.util.*

class Entry(val condition: Condition, val date: Date, val scale: Int, val comment:String, val eventTime: EventTime): Comparable<Entry>{

    override fun compareTo(other: Entry): Int {
        return date.compareTo(other.date)
    }

    fun upload(){
        //Todo get server
    }
}