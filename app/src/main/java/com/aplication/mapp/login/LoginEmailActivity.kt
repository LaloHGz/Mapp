package com.aplication.mapp.login

import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.FocusFinder
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.aplication.mapp.menu.MapActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuthUserCollisionException

import androidx.annotation.NonNull

import android.R.attr.password
import android.app.Dialog
import android.view.Window
import android.widget.*


class LoginEmailActivity : AppCompatActivity() {

    private var emailLayout:TextInputLayout? = null
    private var emailText:TextInputEditText? = null

    private var passwordLayout:TextInputLayout? = null
    private var passwordText:TextInputEditText? = null

    private var progressBar: ProgressBar? = null
    private var btnLogin:AppCompatButton? = null

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        val connection = ConectionManager()
        if(!connection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        database = Firebase.database.reference

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        emailLayout = findViewById(R.id.il_email)
        emailText = findViewById(R.id.et_email)
        passwordLayout = findViewById(R.id.il_password)
        passwordText = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        btnLogin?.setOnClickListener { emailAuth() }

    }

    private fun emailAuth(){
        if(validate()){
            progressBar!!.visibility = View.VISIBLE
            progressBar!!.animate()
            val emailInput = emailText!!.text.toString().trim()
            val passwordInput = passwordText!!.text.toString()
            auth.signInWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener{
                    if (it.isSuccessful) {
                        val intent = Intent(application, MapActivity::class.java)
                        startActivity(intent)
                        finish()
                        finishAffinity()
                    } else {
                        progressBar!!.visibility = View.GONE
                        Toast.makeText(applicationContext, "Email o contraseña no válida", Toast.LENGTH_SHORT).show()
                        emailLayout!!.error = null
                        passwordLayout!!.error = null

                    }
                }
        }
    }


    private fun validate(): Boolean {
        val emailInput = emailText!!.text.toString().trim()
        val passwordInput = passwordText!!.text.toString()
        if (emailInput.isEmpty()){
            emailLayout!!.error = "Campo vacío"
        }
        if(passwordInput.isEmpty()){
            passwordLayout!!.error = "Campo vacío"
        }
        return !TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput) && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()
    }

}