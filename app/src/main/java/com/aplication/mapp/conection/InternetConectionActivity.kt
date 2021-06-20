package com.aplication.mapp.conection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.aplication.mapp.R
import com.google.android.material.snackbar.Snackbar

class InternetConectionActivity : AppCompatActivity() {

    private var layout:ConstraintLayout? = null
    private var backPressedTime: Long = 0
    private var retry:AppCompatButton? = null
    private val conection = ConectionManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_conection)

        layout = findViewById(R.id.layout_internetConection)
        retry = findViewById(R.id.btn_retry)


        if (conection.isOnline(baseContext))
        {
            finish()
        }

        retry?.setOnClickListener {
            if (conection.isOnline(baseContext))
            {
                finish()
            }
        }

    }

    override fun onBackPressed() {
        when {
            conection.isOnline(baseContext) -> {
                finish()
            }
            backPressedTime + 2000> System.currentTimeMillis() -> {
                finishAffinity();
                finish();
            }
            else -> {
                layout?.let { Snackbar.make(it,getString(R.string.backToast),Snackbar.LENGTH_SHORT).show() };
            }
        }
        backPressedTime=System.currentTimeMillis()
    }
}