package mx.lifehealthsolutions.myhealthjournal.models

object User  {
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String

    val conditions_list =  ArrayList<Condition>()
    val medicine_list = ArrayList<Medicine>()


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