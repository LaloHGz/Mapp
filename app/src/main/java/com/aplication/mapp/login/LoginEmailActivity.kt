package com.aplication.mapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.AppCompatButton
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginEmailActivity : AppCompatActivity() {

    private var title:TextView? = null
    private var back:LinearLayout? = null
    private var number:TextView? = null

    private var emailLayout:TextInputLayout? = null
    private var emailText:TextInputEditText? = null

    private var passwordLayout:TextInputLayout? = null
    private var passwordText:TextInputEditText? = null

    private var btnContinue:AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        val conection = ConectionManager()
        if(!conection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        title = findViewById(R.id.tv_title)
        back = findViewById(R.id.back)
        number = findViewById(R.id.tv_number)
        emailLayout = findViewById(R.id.il_email)
        emailText = findViewById(R.id.et_email)
        passwordLayout = findViewById(R.id.il_password)
        passwordText = findViewById(R.id.et_password)
        btnContinue = findViewById(R.id.btn_continue)


        btnContinue?.setOnClickListener { goforward() }

        back?.setOnClickListener { toback() }
    }

    private fun goforward(){
        if(number?.text.toString() == "1/2"){
            if(validateEmail()){
                visibilityPassword()
                number!!.setText("2/2")
                title!!.setText("Ingresa tu contraseña")
            }
            else{
                emailLayout!!.setError("Email no válido")
            }
        }
    }

    private fun toback(){
        if(number?.text.toString() == "1/2"){
            finish()
        }else{
            visibilityEmail()
            number!!.setText("1/2")
            title!!.setText("¿Cuál es tu dirección Email?")
        }
    }

    private fun validateEmail(): Boolean {
        val emailInput: String = emailLayout!!.getEditText()?.getText().toString().trim()
        if (emailInput.isEmpty()){
            emailLayout!!.setError("Campos vacíos")
        }
        return !TextUtils.isEmpty(emailInput) && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()
    }

    private fun visibilityEmail(){
        passwordLayout!!.visibility = View.GONE
        emailLayout!!.visibility = View.VISIBLE
    }

    private fun visibilityPassword(){
        emailLayout!!.visibility = View.GONE
        passwordLayout!!.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if(number?.text.toString() == "1/2"){
            finish()
        }else{
            visibilityEmail()
            number!!.setText("1/2")
            title!!.setText("¿Cuál es tu dirección Email?")
        }

    }
}