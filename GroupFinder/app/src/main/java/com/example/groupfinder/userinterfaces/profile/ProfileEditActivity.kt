package com.example.groupfinder.userinterfaces.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.activity_profile_edit.*

class ProfileEditActivity : AppCompatActivity(){
    private var userInfoHasChange: Boolean = true
    private var userInfo: UserData? = null
    private val replyIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        val bundle: Bundle? = intent.extras
        intent.extras?.let {
            userInfo = intent.extras?.getParcelable("userinfo") as UserData
            nameFieldTextEdit_ActProfileEdit.setText(userInfo?.name)
            courseFieldTextEdit_ActProfileEdit.setText(userInfo?.course)
            RAFieldTextEdit_ActProfileEdit.setText(userInfo?.ra.toString())
        }

        nameFieldTextEdit_ActProfileEdit.addTextChangedListener {
            userInfoHasChange = true
        }
        courseFieldTextEdit_ActProfileEdit.addTextChangedListener {
            userInfoHasChange = true
        }
        RAFieldTextEdit_ActProfileEdit.addTextChangedListener {
            userInfoHasChange = true
        }

        saveFAB_ActProfileEdit.setOnClickListener {
            if(!userInfoHasChange){
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{
                // TODO: Change dummy
                replyIntent.putExtra("replyuserinfo", UserData(
                    RAFieldTextEdit_ActProfileEdit.text.toString().toInt(),
                    nameFieldTextEdit_ActProfileEdit.text.toString(),
                    courseFieldTextEdit_ActProfileEdit.text.toString(), "dummy")
                )
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        // add back arrow to toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
