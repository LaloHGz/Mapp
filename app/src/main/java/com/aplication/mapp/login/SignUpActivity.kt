package com.aplication.mapp.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.aplication.mapp.menu.MapActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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

    private var default:String = "a"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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
        userLayout = findViewById(R.id.il_user)
        userText = findViewById(R.id.et_user)
        emailLayout = findViewById(R.id.il_email)
        emailText = findViewById(R.id.et_email)
        passwordLayout = findViewById(R.id.il_password)
        passwordText = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_signup)


        btnSignUp?.setOnClickListener { signUp() }

    }


    private fun signUp(){
        if (validate()){
            progressBar!!.visibility = View.VISIBLE
            progressBar!!.animate()
            val user = userText!!.text.toString()
            val email = emailText!!.text.toString().trim()
            val password = passwordText!!.text.toString()
            val pd = Dialog(this, android.R.style.Theme_Black)
            pd.setCancelable(false)
            pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
            pd.window!!.setBackgroundDrawableResource(R.color.transparent)
            pd.setContentView(R.layout.activity_sign_up)
            pd.show()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    val id: String? = auth.uid
                    if(task.isSuccessful){
                        // Sign Up success
                        Log.d(TAG, "createUserWithEmailAndPassword:success")

                        val map: MutableMap<String, Any> = HashMap()
                        map["lenguaje"] = "es"
                        map["personaje"] = "Laoshi"
                        map["usuario"] = user
                        map["correo"] = email
                        map["contraseña"] = password
                        map["nivel"] = 1
                        map["Edad"] = 16
                        map["Pais"] = "."
                        map["formato"] = "Written"
                        map["tiempo_diag_t1"] = default
                        map["tiempo_n1t1"] = default
                        map["tiempo_n2t1"] = default
                        map["tiempo_n3t1"] = default
                        map["tiempo_n4t1"] = default
                        map["tiempo_n5t1"] = default
                        map["tiempo_diag_t2"] = default
                        map["tiempo_n1t2"] = default
                        map["tiempo_n2t2"] = default
                        map["tiempo_n3t2"] = default
                        map["tiempo_n4t2"] = default
                        map["tiempo_n5t2"] = default
                        map["tiempo_diag_t3"] = default
                        map["tiempo_n1t3"] = default
                        map["tiempo_n2t3"] = default
                        map["tiempo_n3t3"] = default
                        map["tiempo_n4t3"] = default
                        map["tiempo_n5t3"] = default
                        map["tiempo_diag_t4"] = default
                        map["tiempo_n1t4"] = default
                        map["tiempo_n2t4"] = default
                        map["tiempo_n3t4"] = default
                        map["tiempo_n4t4"] = default
                        map["tiempo_n5t4"] = default
                        map["tiempo_diag_t5"] = default
                        map["tiempo_n1t5"] = default
                        map["tiempo_n2t5"] = default
                        map["tiempo_n3t5"] = default
                        map["tiempo_n4t5"] = default
                        map["tiempo_n5t5"] = default
                        map["tiempo_diag_t6"] = default
                        map["tiempo_n1t6"] = default
                        map["tiempo_n2t6"] = default
                        map["tiempo_n3t6"] = default
                        map["tiempo_n4t6"] = default
                        map["tiempo_n5t6"] = default
                        map["tiempo_diag_t7"] = default
                        map["tiempo_n1t7"] = default
                        map["tiempo_n2t7"] = default
                        map["tiempo_n3t7"] = default
                        map["tiempo_n4t7"] = default
                        map["tiempo_n5t7"] = default
                        map["tiempo_diag_t8"] = default
                        map["tiempo_n1t8"] = default
                        map["tiempo_n2t8"] = default
                        map["tiempo_n3t8"] = default
                        map["tiempo_n4t8"] = default
                        map["tiempo_n5t8"] = default
                        map["tiempo_diag_t9"] = default
                        map["tiempo_n1t9"] = default
                        map["tiempo_n2t9"] = default
                        map["tiempo_n3t9"] = default
                        map["tiempo_n4t9"] = default
                        map["tiempo_n5t9"] = default
                        map["tiempo_diag_t10"] = default
                        map["tiempo_n1t10"] = default
                        map["tiempo_n2t10"] = default
                        map["tiempo_n3t10"] = default
                        map["tiempo_n4t10"] = default
                        map["tiempo_n5t10"] = default

                        database.child("users").child(id!!).setValue(map)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    val intent =
                                        Intent(application, MapActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                    finishAffinity()
                                }
                            }

                    }else {
                        // If sign in fails, display a message to the user
                        Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                        progressBar!!.visibility = View.GONE
                        pd.dismiss()
                        Toast.makeText(this, "Este usuario ya existe / error de registro", Toast.LENGTH_SHORT).show()
                    }
                }
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
        }else if (passwordInput.length < 6){
            passwordLayout!!.error = "contraseña menor a 6 caracteres"
        }
        if (userInput.isEmpty()){
            userLayout!!.error = "Campo vacío"
        }
        if (userInput.length > 15){
            userLayout!!.error = "nombre muy largo"
        }


        return !TextUtils.isEmpty(emailInput) && !TextUtils.isEmpty(passwordInput) && !TextUtils.isEmpty(userInput) && userInput.length <= 15 && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()
    }

    companion object{
        private const val TAG = "SignUpActivity"
    }

}