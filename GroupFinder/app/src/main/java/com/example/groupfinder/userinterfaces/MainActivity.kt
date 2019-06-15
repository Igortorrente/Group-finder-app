package com.example.groupfinder.userinterfaces

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.group.GroupListFragment
import com.example.groupfinder.userinterfaces.profile.ProfileFragment
import com.example.groupfinder.viewmodels.FinderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var profileFragment: ProfileFragment
    private lateinit var groupListFragment: GroupListFragment
    private lateinit var suggestionListFragment: GroupListFragment
    private lateinit var viewModel: FinderViewModel
    private lateinit var toolbar: Menu
    private var lastFragment: Int = 0


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.app_bar_search -> {
                if (lastFragment != 0){
                    toolbar.clear()
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
            R.id.my_groups_navigation -> {
                if (lastFragment != 1){
                    toolbar.clear()
                    menuInflater.inflate(R.menu.group_toolbar, toolbar)
                    lastFragment = 1
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, groupListFragment)
                    .addToBackStack(groupListFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile_navigation -> {
                if (lastFragment != 0){
                    toolbar.clear()
                    lastFragment = 0
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

        viewModel = ViewModelProviders.of(this).get(FinderViewModel::class.java)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, groupListFragment)
            .addToBackStack(groupListFragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        toolbar = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(R.id.group_search == id){

        }
        return super.onOptionsItemSelected(item)
    }
}
