package com.aplication.mapp.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val conection = ConectionManager()
        if(!conection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

    }
}