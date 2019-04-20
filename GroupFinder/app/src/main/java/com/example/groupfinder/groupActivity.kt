package com.example.groupfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_group.*

class groupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        editGroupButton.setOnClickListener { v ->
            val intent = Intent(v.context, groupEditActivity::class.java)
            v.context.startActivity(intent)
        }
    }

}
