package mx.lifehealthsolutions.myhealthjournal.models

import java.util.Date


data class Medicine(val frequency: Frequency, val start_date: Date, val end_date: Date, val name: String, val sickness:Condition) {
}