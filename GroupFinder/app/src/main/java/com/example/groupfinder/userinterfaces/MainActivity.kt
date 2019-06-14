package com.example.groupfinder.userinterfaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.enums.RequestCode
import com.example.groupfinder.userinterfaces.group.GroupListFragment
import com.example.groupfinder.userinterfaces.group.NewGroupActivity
import com.example.groupfinder.userinterfaces.profile.ProfileFragment
import com.example.groupfinder.viewmodels.FinderViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var profileFragment: ProfileFragment
    private lateinit var groupListFragment: GroupListFragment
    private lateinit var suggestionListFragment: GroupListFragment
    private val newGroupRequestCode = RequestCode.NEW_GROUP.number
    private lateinit var viewModel: FinderViewModel
    private lateinit var toolbar: Menu
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

        viewModel = ViewModelProviders.of(this).get(FinderViewModel::class.java)

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
        if(R.id.group_add == id){
            val intent = Intent(this, NewGroupActivity::class.java)
            startActivityForResult(intent, newGroupRequestCode)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newGroupRequestCode){
            if(resultCode == Activity.RESULT_OK){
                data?.let { returnedData ->
                    val group = returnedData.extras?.getParcelable("replyuserinfo") as UserGroups
                    Log.d("intent-user", group.toString())
                    viewModel.insertGroup(group)
                }
                Toast.makeText(this, "Sucesso !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nenhuma mudança", Toast.LENGTH_LONG).show()
            }
        }
    }
}
