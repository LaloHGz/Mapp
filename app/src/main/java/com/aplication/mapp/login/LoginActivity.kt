package com.aplication.mapp.login

import android.R.attr
import android.R.attr.*
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.GoogleApiClient

import androidx.annotation.NonNull
import bolts.Task
import com.aplication.mapp.menu.MapActivity

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import android.app.AlertDialog
import android.app.Dialog

import android.app.ProgressDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.sign
import android.graphics.drawable.ColorDrawable

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.auth.api.signin.GoogleSignIn

import com.facebook.login.LoginManager
import android.R.id
import android.os.Handler
import android.os.Looper
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.facebook.AccessToken
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback
import com.google.firebase.auth.*
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.HashMap


class LoginActivity : AppCompatActivity() {

    private var btnFacebook: AppCompatButton? = null
    private var btnGoogle: AppCompatButton? = null
    private var btnEmail: AppCompatButton? = null
    private var terms:TextView? = null
    private var policy:TextView? = null
    private var register:TextView? = null
    private var progressBar: ProgressBar? = null
    private var url: String = "https://eduardojairhernand.wixsite.com/misitio"

    // [START declare_auth]
    private lateinit var auth:FirebaseAuth
    // [END declare_auth]

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var database: DatabaseReference

    // Initialize Facebook Login button
    private var callbackManager: CallbackManager? = null

    private var email:String? = null
    private var user:String? = null
    private var default:String = "a"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Validate internet conection of device
        val conection = ConectionManager()
        if(!conection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        // Change status bar color to gray
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.gray_10)

        database = Firebase.database.reference

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        btnFacebook = findViewById(R.id.btn_facebook)
        btnGoogle = findViewById(R.id.btn_google)
        btnEmail = findViewById(R.id.btn_email)
        register = findViewById(R.id.tv_register)
        terms = findViewById(R.id.tv_terms)
        policy = findViewById(R.id.tv_policy)

        btnEmail?.setOnClickListener {
            val intent = Intent(this , LoginEmailActivity::class.java)
            startActivity(intent)
        }

        btnGoogle?.setOnClickListener { signInGoogle() }

        btnFacebook?.setOnClickListener { signInFacebook() }

        terms?.setOnClickListener { policy_and_privacy() }

        policy?.setOnClickListener { policy_and_privacy() }

        register?.setOnClickListener {
            val intent = Intent(this , SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...)
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was succesful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:"+account.id)
                firebaseAuthWithGoogle(account)
            }catch (e: ApiException){
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                progressBar!!.visibility = View.GONE
            }

        }

    }
    // [END onactivityresult]

    // [START auth_with_facebook]
    private fun handleFacebookAccessToken(accessToken: AccessToken?) {
        val pd = Dialog(this, android.R.style.Theme_Black)
        pd.setCancelable(false)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.window!!.setBackgroundDrawableResource(R.color.transparent)
        pd.setContentView(R.layout.activity_login)
        pd.show()
        val credential:AuthCredential = FacebookAuthProvider.getCredential(accessToken!!.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                val id: String? = auth.uid
                val account:FirebaseUser = auth.currentUser!!
                database.child("users").child(id!!).addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            email = account.email!!
                            val intent = Intent(application, MapActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }else{

                            if(task.isSuccessful){
                                // Sign In success
                                Log.d("TAG", "signInWithCredential:success")
                                email = account.email!!
                                user = account.displayName!!

                                val map: MutableMap<String, Any> = HashMap()
                                map["lenguaje"] = "es"
                                map["personaje"] = "Laoshi"
                                map["usuario"] = user!!
                                map["correo"] = email!!
                                map["contraseña"] = 0
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

                                database.child("users").child(id).setValue(map)
                                    .addOnCompleteListener { task2 ->
                                        if (task2.isSuccessful) {
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                pd.dismiss()
                                            }, 2000)
                                            val intent =
                                                Intent(application, MapActivity::class.java)
                                            startActivity(intent)
                                            finishAffinity()
                                        }
                                    }

                            }else {
                                // If sign in fails, display a message to the user
                                Log.w(TAG, "signInWithCredential:failure", task.exception)
                                pd.dismiss()

                            }

                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Database:failure", error.toException())
                        pd.dismiss()

                    }


                })


            }
    }
    // [END auth_with_facebook]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val pd = Dialog(this, android.R.style.Theme_Black)
        pd.setCancelable(false)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.window!!.setBackgroundDrawableResource(R.color.transparent)
        pd.setContentView(R.layout.activity_login)
        pd.show()
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                val id: String? = auth.uid
                database.child("users").child(id!!).addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()){
                            email = account.email!!
                            val intent = Intent(application, MapActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }else{

                            if(task.isSuccessful){
                                // Sign In success
                                Log.d("TAG", "signInWithCredential:success")
                                email = account.email!!
                                user = account.displayName!!

                                val map: MutableMap<String, Any> = HashMap()
                                map["lenguaje"] = "es"
                                map["personaje"] = "Laoshi"
                                map["usuario"] = user!!
                                map["correo"] = email!!
                                map["contraseña"] = 0
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

                                database.child("users").child(id).setValue(map)
                                    .addOnCompleteListener { task2 ->
                                        if (task2.isSuccessful) {
                                            Handler(Looper.getMainLooper()).postDelayed({
                                                pd.dismiss()
                                            }, 2000)
                                            val intent =
                                                Intent(application, MapActivity::class.java)
                                            startActivity(intent)
                                            finishAffinity()
                                        }
                                    }

                            }else {
                                // If sign in fails, display a message to the user
                                Log.w(TAG, "signInWithCredential:failure", task.exception)
                                progressBar!!.visibility = View.GONE
                                pd.dismiss()

                            }

                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w(TAG, "Database:failure", error.toException())
                        progressBar!!.visibility = View.GONE
                        pd.dismiss()

                    }


                })


            }
    }
    // [END auth_with_google]

    // [START signinFacebook]
    private fun signInFacebook(){
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    handleFacebookAccessToken(result?.accessToken)
                    Log.d(TAG, "success firebaseAuthWithFacebook")
                }

                override fun onCancel() {
                    Log.d(TAG, "Facebook sign in cancel")
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(applicationContext, "error: "+ error.toString(), Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "Facebook sign in failed", error)
                }
            })
    }
    // [END signinFacebook]

    // [START siginGoogle]
    private fun signInGoogle(){
        progressBar!!.visibility = View.VISIBLE
        progressBar!!.animate()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END siginGoogle]

    private fun policy_and_privacy(){
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object{
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }



}