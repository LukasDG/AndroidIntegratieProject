package be.kdg.integratieproject

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

const val PROJECT_ID: String = "PROJECT_ID"

class MainActivity : AppCompatActivity(){
    private lateinit var navMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseViews()
    }

    private fun initialiseViews(){
        navMenu = findViewById(R.id.bottom_navigation)
        navMenu.setOnNavigationItemSelectedListener(initMenuListener(this))
        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)
    }

    private fun initMenuListener(context: Context): BottomNavigationView.OnNavigationItemSelectedListener{
        return BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.action_home -> {
                    val homeFragment = HomeFragment.newInstance()
                    openFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_search -> {
                    val intent = Intent(context, SearchActivity::class.java)
                    ContextCompat.startActivity(context, intent, null)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    val intent = Intent(context, ProfileActivity::class.java)
                    ContextCompat.startActivity(context, intent, null)
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        }
    }

    fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
