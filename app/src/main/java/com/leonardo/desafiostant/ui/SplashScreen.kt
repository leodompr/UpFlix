package com.leonardo.desafiostant.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leonardo.desafiostant.MainActivity
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.interfacesRetro.RetrofitService
import com.leonardo.desafiostant.model.Movie
import com.leonardo.desafiostant.repositories.MainRepository
import com.leonardo.desafiostant.viewmodel.MainViewModelFactory
import com.leonardo.desafiostant.viewmodel.MianViewModel

class SplashScreen : AppCompatActivity() {
    private var SPLASH_SCREEN_TIME : Long = 1800
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.myLooper()!!).postDelayed({
            intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()

        }, SPLASH_SCREEN_TIME)

    }
}