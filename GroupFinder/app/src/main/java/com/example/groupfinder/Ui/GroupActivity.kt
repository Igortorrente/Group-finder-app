package com.example.groupfinder.Ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.groupfinder.Data.Common.UserGroups
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.activity_group.*

enum class Mode{ ADMIN , USER }
enum class State{ VIEW, EDIT, INSIDE, OUTSIDE}


class GroupActivity : AppCompatActivity() {
    private var group: UserGroups? = null
    private var mode = Mode.ADMIN
    private var state = State.VIEW
    private var infoChange = false
    private var instantChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        intent.extras?.let {
            group = intent.extras?.getParcelable("groupInfo") as UserGroups
            updateTextViews()
            // TODO: Check if user are inside/admin the group
            // Change mode and float button image
        }

        // add back arrow to toolbar
        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        actionGroupButton.setOnClickListener {
            if(mode == Mode.ADMIN){
                if(state == State.EDIT){
                    actionGroupButton.setImageResource(R.drawable.baseline_edit_white_24dp)
                    changeState(View.VISIBLE, View.INVISIBLE)
                    if(instantChange)
                        infoChange = instantChange
                    state = State.VIEW
                    // Change These dummies
                    group = UserGroups(0, subjectFieldTextEdit_ActGroup.text.toString(), "dummy",
                        1,2,
                        0,0, locationFieldTextEdit_ActGroup.text.toString())
                    updateTextViews()

                }else{
                    actionGroupButton.setImageResource(R.drawable.baseline_save_white_24dp)
                    changeState(View.INVISIBLE, View.VISIBLE)
                    state = State.EDIT
                    subjectFieldTextEdit_ActGroup.setText(group?.subject)
                    locationFieldTextEdit_ActGroup.setText(group?.location_description)
                }
            }else {
                if(state == State.INSIDE){
                    actionGroupButton.setImageResource(R.drawable.baseline_person_add_disabled_white_24dp)
                    state = State.OUTSIDE
                }else{
                    actionGroupButton.setImageResource(R.drawable.baseline_group_add_white_24dp)
                    state = State.INSIDE
                }
            }
            instantChange = false
            actionGroupButton
        }

        subjectFieldTextEdit_ActGroup.addTextChangedListener{
            instantChange = true
        }

        locationFieldTextEdit_ActGroup.addTextChangedListener {
            instantChange = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val buttonId = item?.itemId
        if (buttonId == android.R.id.home) {
            backFunction()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        backFunction()
    }

    private fun backFunction(){
        if(mode == Mode.ADMIN){
            if(state == State.EDIT){
                state = State.VIEW
                changeState(View.VISIBLE,  View.INVISIBLE)
                actionGroupButton.setImageResource(R.drawable.baseline_edit_white_24dp)
                instantChange = false
            }else{
                val replyIntent = Intent()
                if(infoChange){
                    replyIntent.putExtra("replytype", 0)
                    replyIntent.putExtra("replygroup", group)
                    setResult(Activity.RESULT_OK, replyIntent)
                }else{
                    setResult(Activity.RESULT_CANCELED, replyIntent)
                }
                finish()
            }
        }else{
            val replyIntent = Intent()
            if(state == State.INSIDE){
                replyIntent.putExtra("replytype", 1)
                setResult(Activity.RESULT_OK, replyIntent)
            }else{
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            finish() // close this activity and return to preview activity (if there is any)
        }
    }

    private fun changeState(mode1: Int, mode2: Int){
        subjectFieldTextView_ActGroup.visibility = mode1
        locationFieldTextView_ActGroup.visibility = mode1

        subjectFieldTextEdit_ActGroup.visibility = mode2
        locationFieldTextEdit_ActGroup.visibility = mode2
    }

    private fun updateTextViews(){
        subjectFieldTextView_ActGroup.text = group?.subject
        locationFieldTextView_ActGroup.text = group?.location_description
        //initTimeTextView_ActNewGroup.text = group?.data_init.toString()
    }
}
