package mx.lifehealthsolutions.myhealthjournal.models

class User (val nombre: String, val email: String, val password: String): Comparable<User> {

    val conditions_list =  ArrayList<Condition>()
    val medicine_list = ArrayList<Medicine>()

    override fun compareTo(other: User): Int {
        return nombre.compareTo(other.nombre)
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

    companion object {
        val arrUsuarios = arrayOf(
            User(
                "Bobby",
                "bobby@itesm.mx",
                "12345678"
            ),
            User(
                "Irving",
                "irving@itesm.mx",
                "12345678"
            ),
            User(
                "Luis",
                "luis@itesm.mx",
                "12345678"
            ),
            User(
                "Rudy",
                "rudy@itesm.mx",
                "12345678"
            )
        )
    }
}