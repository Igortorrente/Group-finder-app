package com.example.groupfinder.Ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var profileFragment: ProfileFragment
    lateinit var groupListFragment: GroupListFragment
    lateinit var suggestionListFragment: GroupListFragment
    lateinit var toolbar: Menu
    private var lastFragment: Int = 0


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.my_groups_navigation -> {
                if (lastFragment != 0){
                    toolbar.clear()
                    menuInflater.inflate(R.menu.group_toolbar, toolbar)
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
                if (lastFragment != 1){
                    toolbar.clear()
                    lastFragment = 1
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
        profileFragment = ProfileFragment()
        groupListFragment = GroupListFragment()
        suggestionListFragment = GroupListFragment()


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
        menuInflater.inflate(R.menu.group_toolbar, toolbar)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(R.id.meeting_add == id){
            //TODO:
        }
        return super.onOptionsItemSelected(item)
    }
}
