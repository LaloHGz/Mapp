package com.aplication.mapp.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.viewpager.widget.ViewPager

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import android.widget.TextView
import com.aplication.mapp.*
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.aplication.mapp.login.LoginActivity
import com.aplication.mapp.menu.MapActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.rd.PageIndicatorView


class OnBoardingActivity : AppCompatActivity() {

    private var skip: TextView? = null
    private var btnNext: AppCompatButton? = null
    private var btnBegin: AppCompatButton? = null


    private var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    private var onBoardingViewPager: ViewPager? = null
    private var position = 0
    private var tabIndicator: PageIndicatorView? = null

    private var mAuth: FirebaseAuth? = null
    private var databaseReference: DatabaseReference? = null

    public override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val map = Intent(this , MapActivity::class.java)
            startActivity(map)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val conection = ConectionManager()
        if(!conection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        //inicializamos el objeto firebaseAuth
        mAuth = FirebaseAuth.getInstance()

        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Mapp: Aprende a programar","Resuelve retos de programación estructurada en lenguaje Python",
            R.drawable.ic_onboarding_1
        ))
        onBoardingData.add(OnBoardingData("Aprende Programación en lenguaje Python","Resuelve problemas de programación para ayudar a los Karankawuas con su lucha contra los extraterrestres Pythonianos",
            R.drawable.ic_onboarding_2
        ))
        onBoardingData.add(OnBoardingData("Personaliza tu perfil","Puedes personalizar tu perfil eligiendo tu personaje favorito de la historia, tu nombre de usuario, tu país, tu edad y el formato en que se mostrará la historia",
            R.drawable.ic_onboarding_3
        ))

        setOnBoardingViewAdapater(onBoardingData)

        btnNext = findViewById(R.id.btn_next)
        btnBegin = findViewById(R.id.btn_begin)
        skip = findViewById(R.id.tv_skip)

        tabIndicator = findViewById(R.id.tab_indicator)

        onBoardingViewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                if (position == onBoardingData.size-1){
                    visibilityButtonOf()
                }
                else
                {
                    visibilityButtonOn()
                }
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageScrollStateChanged(state: Int) {
            }
        })


        skip?.setOnClickListener {
            findPosition()
            if (position < (onBoardingData.size)-1) {
                onBoardingViewPager!!.currentItem = (onBoardingData.size)
            }
        }

        btnNext?.setOnClickListener{
            findPosition()
            if (position < (onBoardingData.size)){
                position++
                onBoardingViewPager!!.currentItem = position
            }
        }

        btnBegin?.setOnClickListener {
            val login = Intent(this , LoginActivity::class.java)
            startActivity(login)
        }

    }


    private fun findPosition(){
        position = onBoardingViewPager!!.currentItem
    }

    private fun visibilityButtonOf() {
        btnNext!!.visibility = View.GONE
        skip!!.visibility = View.GONE
        btnBegin!!.visibility = View.VISIBLE
    }

    private fun visibilityButtonOn() {
        btnNext!!.visibility = View.VISIBLE
        skip!!.visibility = View.VISIBLE
        btnBegin!!.visibility = View.GONE
    }


    private fun setOnBoardingViewAdapater(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.viewpager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
    }


}