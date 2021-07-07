package com.aplication.mapp.menu.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.aplication.mapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.content.Intent

import android.content.ActivityNotFoundException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import com.aplication.mapp.onboarding.OnBoardingActivity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class ProfileFragment: Fragment() {

    private var auth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var edit: AppCompatButton? = null
    private var userImg: ImageView? = null
    private var userName: TextView? = null
    private var medal: ImageView? = null
    private var numLevel: TextView? = null
    private var list: ListView? = null
    private lateinit var googleSignInClient: GoogleSignInClient

    /*
    var mTitle = arrayOf("Formato de la historia", "Califícanos", "Versión de app", "Cerrar sesión")
    var images = intArrayOf(R.drawable.ic_book_black_24,
        R.drawable.ic_sentiment_satisfied_black_24,
        R.drawable.ic_outline_info_black_24,
        R.drawable.ic_exit_to_app_black_24)

     */


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?{
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        database = Firebase.database.reference
        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(view.context, gso)
        // [END config_signin]



        val option1 = OptionProfile(R.drawable.ic_book_black_24, "Formato de la historia")
        val option2 = OptionProfile(R.drawable.ic_sentiment_satisfied_black_24, "Califícanos")
        val option3 = OptionProfile(R.drawable.ic_outline_info_black_24, "Versión de app")
        val option4 = OptionProfile(R.drawable.ic_exit_to_app_black_24, "Cerrar sesión")

        val listOptions = listOf(option1, option2, option3, option4)
        val adapter = ProfileAdapter(view.context, listOptions)


        edit = view.findViewById(R.id.btn_edit)
        userImg = view.findViewById(R.id.img_character)
        userName = view.findViewById(R.id.tv_user)
        medal = view.findViewById(R.id.iv_medal)
        numLevel = view.findViewById(R.id.tv_level_2)
        list = view.findViewById(R.id.listview)

        list!!.adapter = adapter

        getUserInfo()


        list!!.setOnItemClickListener { parent, _view, position, id ->

            if (position == 0) {
                //val i = Intent(activity, EditUserActivity::class.java)
                //i.putExtra("tipo", tipo)
                //startActivity(i)
            }
            if (position == 1) {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + "com.aplication.mapp")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + "com.aplication.mapp")))
                }
            }
            if (position == 3) {
                salir()
            }
        }





        return view
    }


    fun salir() {

        val view = View.inflate(view?.context, R.layout.log_out_card, null)
        val builder = AlertDialog.Builder(view.context)
        builder.setView(view)

        val logOut = builder.create()
        logOut.setCancelable(false)

        logOut.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        logOut.show()

        val accept: AppCompatButton = view.findViewById(R.id.btn_acept)
        val cancel: TextView = view.findViewById(R.id.btn_cancel)

        accept.setOnClickListener {
            auth!!.signOut()
            LoginManager.getInstance().logOut()
            val intent = Intent(activity, OnBoardingActivity::class.java)
            GoogleSignIn.getClient(
                view.context,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            ).signOut()
            startActivity(intent)
            activity?.finishAffinity()
        }

        cancel.setOnClickListener { logOut.dismiss() }

    }






    private fun getUserInfo(){
        var us:String? = null
        var character: String? = null
        var lev: String? = null
        val id: String? = auth!!.uid
        database!!.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    us = snapshot.child("usuario").value.toString()
                    character = snapshot.child("personaje").value.toString()
                    lev = snapshot.child("nivel").value.toString()

                    userName!!.text = us!!.substring(0,15)
                    numLevel!!.text = lev

                    when (character) {
                        "Laoshi" -> {
                            userImg!!.setImageResource(R.drawable.ic_laoshi_pp)
                        }
                        "Tizoc" -> {
                            userImg!!.setImageResource(R.drawable.ic_tizoc_pp)
                        }
                        "Houyi" -> {
                            userImg!!.setImageResource(R.drawable.houyi)
                        }
                        "Kin" -> {
                            userImg!!.setImageResource(R.drawable.kin)
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }


}