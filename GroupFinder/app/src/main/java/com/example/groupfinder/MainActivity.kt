package com.example.groupfinder

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var profileFragment: profileFragment
    lateinit var groupListFragment: groupListFragment
    lateinit var suggestionListFragment: groupListFragment
    lateinit var toolbar: Menu
    private var lastFragment: Int = 0


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.my_groups_navigation -> {
                if (lastFragment != 0){
                        menuInflater.inflate(R.menu.search_toolbar, toolbar)
                        lastFragment = 0
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, groupListFragment)
                    .addToBackStack(groupListFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.sugestions_navigation -> {
                if (lastFragment != 0){
                    menuInflater.inflate(R.menu.search_toolbar, toolbar)
                    lastFragment = 0
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, suggestionListFragment)
                    .addToBackStack(suggestionListFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_navigation -> {
                if (lastFragment != 1){
                    toolbar.clear()
                    lastFragment = 1
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, profileFragment)
                    .addToBackStack(profileFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()

                layoutInflater.inflate(R.layout.fragment_profile, container, false)
                
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        profileFragment = profileFragment()
        groupListFragment = groupListFragment()
        suggestionListFragment = groupListFragment()


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, groupListFragment)
            .addToBackStack(groupListFragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

        //177953

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        toolbar = menu!!
        menuInflater.inflate(R.menu.search_toolbar, toolbar)
        return super.onCreateOptionsMenu(menu)
    }
}
