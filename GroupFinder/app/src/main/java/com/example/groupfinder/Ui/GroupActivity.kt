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
    private var mode = Mode.USER
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
                    group = UserGroups(0, subjectFieldTextEdit.text.toString(), "dummy",
                        dataInitFieldTextEdit.text.toString().toInt(), dataEndFieldTextEdit.text.toString().toInt(),
                        0,0, locationFieldTextEdit.text.toString())
                    updateTextViews()

                }else{
                    actionGroupButton.setImageResource(R.drawable.baseline_save_white_24dp)
                    changeState(View.INVISIBLE, View.VISIBLE)
                    state = State.EDIT
                    subjectFieldTextEdit.setText(group?.subject)
                    locationFieldTextEdit.setText(group?.location_description)
                    dataInitFieldTextEdit.setText(group?.data_init.toString())
                    dataEndFieldTextEdit.setText(group?.data_end.toString())
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

        subjectFieldTextEdit.addTextChangedListener{
            instantChange = true
        }

        locationFieldTextEdit.addTextChangedListener {
            instantChange = true
        }

        dataInitFieldTextEdit.addTextChangedListener{
            instantChange = true
        }

        dataEndFieldTextEdit.addTextChangedListener{
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
        subjectFieldTextView.visibility = mode1
        locationFieldTextView.visibility = mode1
        initTimeTextView.visibility = mode1
        dataEndFieldTextView.visibility = mode1

        subjectFieldTextEdit.visibility = mode2
        locationFieldTextEdit.visibility = mode2
        dataInitFieldTextEdit.visibility = mode2
        dataEndFieldTextEdit.visibility = mode2
    }

    private fun updateTextViews(){
        subjectFieldTextView.text = group?.subject
        locationFieldTextView.text = group?.location_description
        initTimeTextView.text = group?.data_init.toString()
        dataEndFieldTextView.text = group?.data_end.toString()
    }
}
