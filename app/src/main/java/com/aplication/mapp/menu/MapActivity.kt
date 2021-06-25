package com.aplication.mapp.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.aplication.mapp.R
import com.aplication.mapp.conection.ConectionManager
import com.aplication.mapp.conection.InternetConectionActivity
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView

class MapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val conection = ConectionManager()
        if(!conection.isOnline(baseContext)){
            val intent = Intent(application, InternetConectionActivity::class.java)
            startActivity(intent)
        }

        val homeFragment = HomeFragment()
        val theoryFragment = TheoryFragment()
        val problemsFragment = ProblemsFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home ->{
                    setCurrentFragment(homeFragment)
                    Log.i(TAG, "Home selected")
                }
                R.id.nav_theory ->{
                    setCurrentFragment(theoryFragment)
                    Log.i(TAG, "Theory selected")
                }
                R.id.nav_problems ->{
                    setCurrentFragment(problemsFragment)
                    Log.i(TAG, "Problems selected")
                }
                R.id.nav_profile ->{
                    setCurrentFragment(profileFragment)
                    Log.i(TAG, "Profile selected")
                }
            }
            true
        }





    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }

    companion object{
        private const val TAG = "MapActivity"
    }
}