package com.aplication.mapp.menu

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
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment: Fragment() {

    private var auth: FirebaseAuth? = null
    private var database: DatabaseReference? = null
    private var edit: AppCompatButton? = null
    private var imgUser: CircleImageView? = null
    private var nameUser: TextView? = null
    private var medal: ImageView? = null
    private var numLevel: TextView? = null
    private var list: ListView? = null

    var mTitle = arrayOf("Formato de la historia", "Califícanos", "Versión de app", "Cerrar sesión")
    var images = intArrayOf(R.drawable.ic_book_black_24,
        R.drawable.ic_sentiment_satisfied_black_24,
        R.drawable.ic_outline_info_black_24,
        R.drawable.ic_exit_to_app_black_24)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        database = Firebase.database.reference

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]


        edit = view.findViewById(R.id.btn_edit)
        imgUser = view.findViewById(R.id.circleImageView_profile)
        nameUser = view.findViewById(R.id.tv_user)
        medal = view.findViewById(R.id.iv_medal)
        numLevel = view.findViewById(R.id.tv_level_2)
        list = view.findViewById(R.id.listview)





        return view
    }

    


    private fun getUserInfo(){
        var us:String? = null
        var character: String? = null
        val id: String? = auth!!.uid
        database!!.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    us = snapshot.child("usuario").value.toString()
                    character = snapshot.child("personaje").value.toString()

                    /*userName!!.text = us!!.substring(0,15)
                    userWelcome!!.text = us!!.substring(0,15)

                    if (character.equals("Laoshi")){
                        userImg!!.setImageResource(R.drawable.ic_laoshi_pp)
                    }else if(character.equals("Tizoc")){
                        userImg!!.setImageResource(R.drawable.ic_tizoc_pp)
                    }else if(character.equals("Houyi")){
                        userImg!!.setImageResource(R.drawable.houyi)
                    }else if(character.equals("Kin")){
                        userImg!!.setImageResource(R.drawable.kin)
                    }

                     */

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }


}