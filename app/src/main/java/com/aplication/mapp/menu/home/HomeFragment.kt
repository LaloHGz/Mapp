package com.aplication.mapp.menu.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aplication.mapp.R
import com.facebook.shimmer.ShimmerFrameLayout
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
    private var shimmerFrameLayout: ShimmerFrameLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout)


        val topView: View = view.findViewById(R.id.view2)
        val imageLogo: ImageView = view.findViewById(R.id.imageView4)
        userName = view.findViewById(R.id.tv_user)
        userImg = view.findViewById<CircleImageView>(R.id.circleImageView_profile)
        val subtitle1: TextView = view.findViewById(R.id.tv_subtitle_1)
        userWelcome = view.findViewById(R.id.tv_user_2)
        val subtitle2: TextView = view.findViewById(R.id.textView5)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        database = Firebase.database.reference

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = Firebase.auth
        // [END initialize_auth]

        getUserInfo(topView, imageLogo, subtitle1, subtitle2, recyclerView)

        val bottom: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)

        // Obtain Frame Layout to the fragments of the Activity Map
        val fragment1 = requireActivity().findViewById<FrameLayout>(R.id.fragment_container)
        val fragment2 = requireActivity().findViewById<FrameLayout>(R.id.fragment_container2)
        val fragment3 = requireActivity().findViewById<FrameLayout>(R.id.fragment_container3)
        val fragment4 = requireActivity().findViewById<FrameLayout>(R.id.fragment_container4)


        userImg!!.setOnClickListener{
            // Change the visibility of the Frame Layout of the Fragments to switch to a profile Fragment Frame Layout
            fragment1.visibility = View.GONE
            fragment2.visibility = View.GONE
            fragment3.visibility = View.GONE
            fragment4.visibility = View.VISIBLE
            bottom.menu.findItem(R.id.nav_profile).isChecked = true
        }

        val adapter = LessonAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter


        return view
    }


    // Obtain the data user of Firebase that require in the image, user name and a welcome text view
    private fun getUserInfo(topView: View, imageLogo: ImageView, subtitle1: TextView, subtitle2: TextView, recyclerView: RecyclerView){
        var us:String? = null
        var character: String? = null
        val id: String? = auth.uid


        database.child("users").child(id!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //snapshot.
                if(snapshot.exists()){
                    us = snapshot.child("usuario").value.toString()
                    character = snapshot.child("personaje").value.toString()

                    if (us!!.length > 15 ){
                        userName!!.text = us!!.substring(0,15)
                        userWelcome!!.text = us!!.substring(0,15)
                    }else{
                        userName!!.text = us
                        userWelcome!!.text = us
                    }

                    if (character.equals("Laoshi")){
                        userImg!!.setImageResource(R.drawable.ic_laoshi_pp)
                    }else if(character.equals("Tizoc")){
                        userImg!!.setImageResource(R.drawable.ic_tizoc_pp)
                    }else if(character.equals("Houyi")){
                        userImg!!.setImageResource(R.drawable.houyi)
                    }else if(character.equals("Kin")){
                        userImg!!.setImageResource(R.drawable.kin)
                    }

                    shimmerFrameLayout!!.stopShimmer()
                    shimmerFrameLayout!!.hideShimmer()
                    shimmerFrameLayout!!.visibility = View.GONE
                    topView.visibility = View.VISIBLE
                    imageLogo.visibility = View.VISIBLE
                    userName!!.visibility = View.VISIBLE
                    userImg!!.visibility =View.VISIBLE
                    subtitle1.visibility = View.VISIBLE
                    userWelcome!!.visibility = View.VISIBLE
                    subtitle2.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE



                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

    }





}