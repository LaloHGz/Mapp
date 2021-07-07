package com.aplication.mapp.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.aplication.mapp.menu.home.HomeFragment
import com.aplication.mapp.menu.problems.ProblemsFragment
import com.aplication.mapp.menu.profile.ProfileFragment
import com.aplication.mapp.menu.theory.TheoryFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val connection = ConectionManager()
        if(!connection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        val homeFragment = HomeFragment()
        val theoryFragment = TheoryFragment()
        val problemsFragment = ProblemsFragment()
        val profileFragment = ProfileFragment()


        val fragment1 = findViewById<FrameLayout>(R.id.fragment_container)
        val fragment2 = findViewById<FrameLayout>(R.id.fragment_container2)
        val fragment3 = findViewById<FrameLayout>(R.id.fragment_container3)
        val fragment4 = findViewById<FrameLayout>(R.id.fragment_container4)

        fragment1.visibility = View.VISIBLE


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, homeFragment)
            .commit()


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container2, theoryFragment)
            .commit()


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container3, problemsFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container4, profileFragment)
            .commit()



        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home ->{
                    fragment1.visibility = View.VISIBLE
                    fragment2.visibility = View.GONE
                    fragment3.visibility = View.GONE
                    fragment4.visibility = View.GONE
                    //setCurrentFragment(homeFragment)
                    Log.i(TAG, "Home selected")
                }
                R.id.nav_theory ->{
                    fragment1.visibility = View.GONE
                    fragment2.visibility = View.VISIBLE
                    fragment3.visibility = View.GONE
                    fragment4.visibility = View.GONE

                    //setCurrentFragment(theoryFragment)
                    Log.i(TAG, "Theory selected")
                }
                R.id.nav_problems ->{
                    fragment1.visibility = View.GONE
                    fragment2.visibility = View.GONE
                    fragment3.visibility = View.VISIBLE
                    fragment4.visibility = View.GONE
                    //setCurrentFragment(problemsFragment)
                    Log.i(TAG, "Problems selected")
                }
                R.id.nav_profile ->{
                    fragment1.visibility = View.GONE
                    fragment2.visibility = View.GONE
                    fragment3.visibility = View.GONE
                    fragment4.visibility = View.VISIBLE

                    //setCurrentFragment(profileFragment)
                    Log.i(TAG, "Profile selected")
                }
            }
            true
        }



    }


    // se cambio por un intercambio de vistas de Frame Layout para evitar que cargue más veces los datos
    // it change for a switch of views of the Frame Layout to avoid to charge more than once the data of Firebase
    /*private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragment)
            commit()
        }

     */

    companion object{
        private const val TAG = "MapActivity"
    }
}