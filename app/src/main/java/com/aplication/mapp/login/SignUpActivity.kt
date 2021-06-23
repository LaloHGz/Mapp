package com.aplication.mapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private var emailLayout: TextInputLayout? = null
    private var emailText: TextInputEditText? = null

    private var passwordLayout: TextInputLayout? = null
    private var passwordText: TextInputEditText? = null

    private var userLayout: TextInputLayout? = null
    private var userText: TextInputEditText? = null

    private var progressBar: ProgressBar? = null
    private var btnSignUp: AppCompatButton? = null

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val conection = ConectionManager()
        if(!conection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        database = Firebase.database.reference

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        userLayout = findViewById(R.id.il_user)
        userText = findViewById(R.id.et_user)
        emailLayout = findViewById(R.id.il_email)
        emailText = findViewById(R.id.et_email)
        passwordLayout = findViewById(R.id.il_password)
        passwordText = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_signup)


        btnSignUp?.setOnClickListener {
            validate()
        }




    }

    private fun validate(): Boolean {
        val emailInput = emailText!!.text.toString().trim()
        val passwordInput = passwordText!!.text.toString()
        val userInput = userText!!.text.toString()
        if (emailInput.isEmpty()){
            emailLayout!!.error = "Campo vacío"
        }
        if(passwordInput.isEmpty()){
            passwordLayout!!.error = "Campo vacío"
        }
        if (userInput.isEmpty()){
            userLayout!!.error = "Campo vacío"
        }

        return !TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(userInput) && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()
    }

}