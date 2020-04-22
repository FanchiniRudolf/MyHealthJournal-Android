package mx.lifehealthsolutions.myhealthjournal.models

import java.util.*

class Entry(val condition: Condition, val date: Date, val scale: Int, val comment:String, val eventTime: String): Comparable<Entry>{


    override fun compareTo(other: Entry): Int {
        return date.compareTo(other.date)
    }

    fun upload(){
        //Todo get server
    }
    companion object {
        val arrEntradas = arrayOf(
            Entry(Condition.arrCondiciones[0], Date(), 5,"Detalles...", "ads"),
            Entry(Condition.arrCondiciones[1], Date(), 7,"Detalles...", "ads"),
            Entry(Condition.arrCondiciones[1], Date(), 7,"Detalles...", "ads")
        )
    }
}