package com.example.groupfinder.Ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        actionGroupButton.setOnClickListener { v ->
            val intent = Intent(v.context, GroupEditActivity::class.java)
            v.context.startActivity(intent)
        }

        // add back arrow to toolbar
        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        actionGroupButton.setOnClickListener { view ->
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item)
    }

}
