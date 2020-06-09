package mx.lifehealthsolutions.myhealthjournal.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import mx.lifehealthsolutions.myhealthjournal.R
import java.lang.Exception

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val background = object: Thread() {
            override fun run(){
                try{
                    Thread.sleep(2000)
                    val mainIntent = Intent(baseContext, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
        val anim = AnimationUtils.loadAnimation(this,R.anim.fadein)
        val im = findViewById(R.id.logoSplash) as ImageView
        val tv = findViewById(R.id.txtSplash) as TextView

        im.startAnimation(anim)
        tv.startAnimation(anim)
    }
}