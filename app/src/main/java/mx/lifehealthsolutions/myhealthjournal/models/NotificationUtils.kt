package mx.lifehealthsolutions.myhealthjournal.models

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import java.util.*

class NotificationUtils {

    fun setNotification(timeInMilliSeconds: Long, time: String, date : String, activity:Activity){

        if (timeInMilliSeconds > 0){

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java)

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)

            val strDate = date.split("/").toTypedArray()
            Log.w("my date arr", "${strDate[0].toInt()}")
            Log.w("my date arr", "${strDate[1].toInt()}")
            Log.w("my date arr", "${strDate[2].toInt()}")
            val strTime = time.split(":").toTypedArray()

            val dia = strDate[0].toInt()
            val mes = strDate[1].toInt()
            val anio = strDate[2].toInt()

            val hora = strTime[0].toInt()
            val minutos = strTime[1].toInt()

            Log.w("Health journal time", "La fecha es ${dia}, ${mes}, ${anio}")

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.DAY_OF_MONTH, dia)
                set(Calendar.MONTH, mes)
                set(Calendar.YEAR, anio)
                set(Calendar.HOUR_OF_DAY, hora)
                set(Calendar.MINUTE, minutos)
            }

            Log.d("Health journal time", "Calendario es ${calendar.timeInMillis}")

            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT)

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000,AlarmManager.INTERVAL_HOUR, pendingIntent)
        }
    }
}