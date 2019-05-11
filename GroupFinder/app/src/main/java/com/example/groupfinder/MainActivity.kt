package com.example.groupfinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*

class MainActivity : AppCompatActivity() {
    lateinit var profileFragment: profileFragment
    lateinit var groupListFragment: groupListFragment
    lateinit var suggestionListFragment: groupListFragment


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.my_groups_navigation -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, groupListFragment)
                    .addToBackStack(groupListFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.sugestions_navigation -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, suggestionListFragment)
                    .addToBackStack(suggestionListFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_navigation -> {
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

    }

}
