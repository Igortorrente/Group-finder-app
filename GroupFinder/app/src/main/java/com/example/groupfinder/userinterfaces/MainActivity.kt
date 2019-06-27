package com.example.groupfinder.userinterfaces

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.groupfinder.Data.Prefs
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.group.GroupListFragment
import com.example.groupfinder.userinterfaces.login.LoginActivity
import com.example.groupfinder.userinterfaces.profile.ProfileFragment
import com.example.groupfinder.userinterfaces.search.GroupSearchFragment
import com.example.groupfinder.viewmodels.FinderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var profileFragment: ProfileFragment
    private lateinit var groupListFragment: GroupListFragment
    private lateinit var groupSearchListFragment: GroupSearchFragment
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
                    .replace(R.id.container, groupSearchListFragment)
                    .addToBackStack(groupSearchListFragment.toString())
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
        groupSearchListFragment = GroupSearchFragment()

        viewModel = ViewModelProviders.of(this).get(FinderViewModel::class.java)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, groupSearchListFragment)
            .addToBackStack(groupSearchListFragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        val result =  super.onCreateView(name, context, attrs)
        /*
        if (Prefs(context!!).userRa < 0) {
            val intent = Intent(context!!, LoginActivity::class.java)
            context!!.startActivity(intent)
        }
        */
        return result
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
