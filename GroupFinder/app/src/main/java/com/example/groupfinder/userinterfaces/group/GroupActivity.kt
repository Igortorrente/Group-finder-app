package com.example.groupfinder.userinterfaces.group

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.dialogs.DatePickDialog
import com.example.groupfinder.userinterfaces.dialogs.TimePickDialog
import com.example.groupfinder.userinterfaces.enums.Caller
import com.example.groupfinder.userinterfaces.enums.Mode
import com.example.groupfinder.userinterfaces.enums.State
import kotlinx.android.synthetic.main.activity_group.*
import java.text.DateFormat
import java.util.*


class GroupActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var dialogCaller = Caller.TIME_INIT
    private val datePicker = DatePickDialog()
    private val timePicker = TimePickDialog()
    private var group: UserGroups? = null
    private var mode = Mode.ADMIN
    private var state = State.VIEW
    private var infoChange = false
    private var instantChange = false
    private val contents: MutableList<Content> = arrayListOf()
    private lateinit var contentRecyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ContentRecyclerViewAdapter
    private lateinit var contentTouchHelper: ItemTouchHelper

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
                    if(instantChange)
                        infoChange = instantChange
                    actionGroupButton.setImageResource(R.drawable.baseline_edit_white_24dp)
                    addContentFAB_ActGroup.hide()
                    changeState(View.VISIBLE, View.INVISIBLE)
                    contentTouchHelper.attachToRecyclerView(null)
                    endDayTextView_ActGroup.setOnClickListener(null)
                    initDayTextView_ActGroup.setOnClickListener(null)
                    initTimeTextView_ActGroup.setOnClickListener(null)
                    endTimeTextView_ActGroup.setOnClickListener(null)

                    // TODO: Change These dummies
                    group = UserGroups(
                        0, subjectFieldTextEdit_ActGroup.text.toString(), "dummy",
                        1, 2,
                        0, 0, locationFieldTextEdit_ActGroup.text.toString()
                    )
                    updateTextViews()
                    state = State.VIEW
                }else{
                    actionGroupButton.setImageResource(R.drawable.baseline_save_white_24dp)
                    addContentFAB_ActGroup.show()
                    changeState(View.INVISIBLE, View.VISIBLE)
                    contentTouchHelper.attachToRecyclerView(contentRecyclerView)
                    subjectFieldTextEdit_ActGroup.setText(group?.subject)
                    locationFieldTextEdit_ActGroup.setText(group?.location_description)

                    endDayTextView_ActGroup.setOnClickListener{
                        datePicker.show(supportFragmentManager, "init date picker")
                        dialogCaller = Caller.DATE_END
                    }
                    initDayTextView_ActGroup.setOnClickListener{
                        datePicker.show(supportFragmentManager, "end date picker")
                        dialogCaller = Caller.DATE_INIT
                    }
                    initTimeTextView_ActGroup.setOnClickListener{
                        timePicker.show(supportFragmentManager, "init hour picker")
                        dialogCaller = Caller.TIME_INIT
                    }
                    endTimeTextView_ActGroup.setOnClickListener{
                        timePicker.show(supportFragmentManager, "end hour picker")
                        dialogCaller = Caller.TIME_END
                    }
                    state = State.EDIT
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

        addContentFAB_ActGroup.setOnClickListener {
            recyclerViewAdapter.addContent(Content(0,
                "test: https://www.youtube.com/watch?v=Cys_i-6Pu-o",""))
        }

        addContentFAB_ActGroup.hide()

        // Content RecycleView
        contentRecyclerView = findViewById(R.id.content_recycler_view)
        recyclerViewAdapter = ContentRecyclerViewAdapter(contents, this)
        contentRecyclerView.adapter = recyclerViewAdapter
        contentTouchHelper = ItemTouchHelper(ContentSwipeToDeleteCallback(recyclerViewAdapter))
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
                contentTouchHelper.attachToRecyclerView(null)
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
        descriptionFieldTextView_ActGroup.visibility = mode1

        subjectFieldTextEdit_ActGroup.visibility = mode2
        locationFieldTextEdit_ActGroup.visibility = mode2
        descriptionFieldTextEdit_ActGroup.visibility = mode2

    }

    private fun updateTextViews(){
        subjectFieldTextView_ActGroup.text = group?.subject
        locationFieldTextView_ActGroup.text = group?.location_description
        //descriptionFieldTextEdit_ActGroup.text = group?.
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        if(dialogCaller == Caller.DATE_INIT){
            initDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(calendar.time)
        }else {
            endDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(calendar.time)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val text = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        if(dialogCaller == Caller.TIME_INIT){
            initTimeTextView_ActGroup.text = text
        }else {
            endTimeTextView_ActGroup.text = text
        }
    }
}
