package com.aplication.mapp.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplication.mapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView


class HomeFragment: Fragment() {

    // [START declare_auth]
    private lateinit var auth:FirebaseAuth
    // [END declare_auth]

    private lateinit var database: DatabaseReference

    private var userName: TextView? = null
    private var userImg: CircleImageView? = null
    private var userWelcome: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        database = Firebase.database.reference

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        getUserInfo()

        userName = view.findViewById(R.id.tv_user)
        userImg = view?.findViewById<CircleImageView>(R.id.circleImageView_profile)
        userWelcome = view.findViewById(R.id.tv_user_2)

        val bottom: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)

        userImg!!.setOnClickListener{
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction!!.replace(R.id.fragment_container, ProfileFragment())
            bottom.menu.findItem(R.id.nav_profile).isChecked = true;
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = LessonAdapter()

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter


        return view
    }


    private fun getUserInfo(){
        var us:String? = null
        var character: String? = null
        val id: String? = auth.uid
        database.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    us = snapshot.child("usuario").value.toString()
                    character = snapshot.child("personaje").value.toString()

                    userName!!.text = us!!.substring(0,15)
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

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }





}