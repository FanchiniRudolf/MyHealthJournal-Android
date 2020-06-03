package mx.lifehealthsolutions.myhealthjournal.models

class Entry(val condition: String, val date: String, val scale: Long, val comment:String, val eventTime: Long): Comparable<Entry>{


    override fun compareTo(other: Entry): Int {
        return date.compareTo(other.date)
    }

    fun upload(){
        //Todo get server
    }
    companion object {
        val arrEntradas = arrayOf(
            Entry("migraña", "" , 5,"Detalles...", 1),
            Entry("migraña", "", 7,"Detalles...", 1),
            Entry("migraña", "", 0,"Detalles...", 1)
        )
    }
}