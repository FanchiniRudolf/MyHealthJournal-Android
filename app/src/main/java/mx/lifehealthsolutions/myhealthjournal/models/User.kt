package mx.lifehealthsolutions.myhealthjournal.models

class User (val nombre: String, val email: String, val password: String): Comparable<User> {
    override fun compareTo(other: User): Int {
        return nombre.compareTo(other.nombre)
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