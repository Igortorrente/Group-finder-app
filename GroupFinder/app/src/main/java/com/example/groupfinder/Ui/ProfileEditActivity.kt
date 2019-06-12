package com.example.groupfinder.Ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.groupfinder.Data.Common.Class
import com.example.groupfinder.Data.Common.UserData
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.activity_profile_edit.*
import java.util.ArrayList

class ProfileEditActivity : AppCompatActivity(){
    private var userInfoHasChange: Boolean = true
    private var userClassesHasChange: Boolean = false
    private var classes: List<Class>? = null
    private var userInfo: UserData? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        val bundle: Bundle? = intent.extras
        intent.extras?.let {
            userInfo = intent.extras?.getParcelable("userinfo") as UserData
            subjectFieldTextView.setText(userInfo?.name)
            locationFieldTextView.setText(userInfo?.course)
            dataInitFieldTextView.setText(userInfo?.ra.toString())

            classes = intent.extras?.getParcelableArrayList<Class>("userclasses") as List<Class>

            // TODO: Implement This aClasses
        }

        subjectFieldTextView.addTextChangedListener {
            userInfoHasChange = true
        }
        locationFieldTextView.addTextChangedListener {
            userInfoHasChange = true
        }
        dataInitFieldTextView.addTextChangedListener {
            userInfoHasChange = true
        }

        // add back arrow to toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val replyIntent = Intent()

        if(R.id.saveButton == item?.itemId){
            if(!userInfoHasChange){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{
                // TODO: Change dummy
                replyIntent.putExtra("replyuserinfo", UserData(
                    dataInitFieldTextView.text.toString().toInt(),
                    subjectFieldTextView.text.toString(), locationFieldTextView.text.toString(), "dummy")
                )
                // TODO: rework at this
                replyIntent.putParcelableArrayListExtra("replyuserclasses", classes as ArrayList<out Parcelable>)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
        if (item?.itemId == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
