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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.api.Utils
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.dialogs.DatePickDialog
import com.example.groupfinder.userinterfaces.dialogs.TimePickDialog
import com.example.groupfinder.userinterfaces.enums.Caller
import com.example.groupfinder.userinterfaces.enums.Mode
import com.example.groupfinder.userinterfaces.enums.State
import com.example.groupfinder.userinterfaces.enums.UserState
import com.example.groupfinder.viewmodels.FinderViewModel
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_new_group.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class GroupActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var dialogCaller = Caller.TIME_INIT
    private val datePicker = DatePickDialog()
    private val timePicker = TimePickDialog()
    private var group: UserGroups? = null
    private var mode = Mode.ADMIN
    private var groupState = GroupState(State.VIEW)
    private var infoChange = false
    private var instantChange = false
    private val contents: MutableList<Content> = arrayListOf()
    private lateinit var contentRecyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ContentRecyclerViewAdapter
    private lateinit var contentTouchHelper: ItemTouchHelper
    private var groupID: Int = -1

    private lateinit var viewModel: FinderViewModel

    private var curInitDate: String? = null
    private var curEndDate: String? = null

    private val defDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        viewModel = this?.run {
            ViewModelProviders.of(this).get(FinderViewModel::class.java)
        }!!

        viewModel.changeContext(this)

        intent.extras?.let {
            val state =  intent.extras?.get("state") as UserState
            group = intent.extras?.getParcelable("groupArg-info") as UserGroups

            groupID = group?.id!!

            updateTextViews()

            when (state) {
                UserState.OUTSIDE -> {
                    mode = Mode.USER
                    groupState.state = State.OUTSIDE
                    actionGroupButton.setImageResource(R.drawable.baseline_person_add_disabled_white_24dp)
                }
                UserState.INSIDE -> {
                    mode = Mode.USER
                    groupState.state = State.INSIDE
                    actionGroupButton.setImageResource(R.drawable.baseline_group_add_white_24dp)
                }
                else -> {
                    // Save FloatActionButton listener
                    saveFAB_ActGroup.setOnClickListener {
                        //Utils.showAlertDialog(this, "Save Dialog", "Save Dialog Opened")
                        val replyIntent = Intent()
                        replyIntent.putExtra("reply-type", 0)
                        replyIntent.putExtra("reply-groupArg-info", group)
                        setResult(Activity.RESULT_OK, replyIntent)
                        finish()
                    }
                }
            }
        }

        // add back arrow to toolbar
        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        actionGroupButton.setOnClickListener {
            if(mode == Mode.ADMIN){
                if(groupState.state == State.EDIT){
                    actionGroupButton.setImageResource(R.drawable.baseline_edit_white_24dp)
                    //Hide the add content FloatActionButton
                    addContentFAB_ActGroup.hide()
                    // Change textEdit to textView of static fields
                    changeState(View.VISIBLE, View.INVISIBLE)
                    // Disable The slide listener
                    contentTouchHelper.attachToRecyclerView(null)
                    // Disable all date/time pickers listeners
                    endDayTextView_ActGroup.setOnClickListener(null)
                    initDayTextView_ActGroup.setOnClickListener(null)
                    initTimeTextView_ActGroup.setOnClickListener(null)
                    endTimeTextView_ActGroup.setOnClickListener(null)

                    // TODO: Change These dummies
                    group = UserGroups(
                        groupID, subjectFieldTextEdit_ActGroup.text.toString(), descriptionFieldTextEdit_ActGroup.text.toString(),
                        "$curInitDate ${initTimeTextView_ActGroup.text}", "$curEndDate ${endTimeTextView_ActGroup.text}",
                        viewModel.getCurrentRA(), locationFieldTextEdit_ActGroup.text.toString()
                    )

                    if(instantChange){
                        infoChange = true
                        saveFAB_ActGroup.show()
                    }else if(infoChange){
                        saveFAB_ActGroup.show()
                    }

                    // Update The texts
                    updateTextViews()
                    // Update state variable
                    groupState.state = State.VIEW
                }else{
                    // Change Main FloatActionButton icons
                    actionGroupButton.setImageResource(R.drawable.ic_baseline_non_edit_24px)
                    // Show add content FloatActionButton
                    addContentFAB_ActGroup.show()
                    // Change textView to textEdit of static fields
                    changeState(View.INVISIBLE, View.VISIBLE)
                    // Enable The slide listener
                    contentTouchHelper.attachToRecyclerView(contentRecyclerView)
                    subjectFieldTextEdit_ActGroup.setText(group?.subject)
                    descriptionFieldTextEdit_ActGroup.setText(group?.detail)
                    locationFieldTextEdit_ActGroup.setText(group?.location_description)

                    curInitDate = group?.data_init!!.split(" ")[0]
                    val initDate: Date = defDateFormat.parse(curInitDate)
                    initDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(initDate)

                    curEndDate = group?.data_end!!.split(" ")[0]
                    val endDate: Date = defDateFormat.parse(curEndDate)
                    endDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(endDate)

                    initTimeTextView_ActGroup.text = group?.data_init!!.split(" ")[1]
                    endTimeTextView_ActGroup.text = group?.data_end!!.split(" ")[1]

                    // Enable all date/time pickers listeners
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

                    // Do Backup of the data
                    recyclerViewAdapter.doBackup()

                    // Hide save fab
                    saveFAB_ActGroup.hide()

                    // Update state variable
                    groupState.state = State.EDIT
                }
                // Reload content recycler view
                recyclerViewAdapter.reload()
            }else {
                if(groupState.state == State.INSIDE){
                    // Change de FloatActionButton icon
                    actionGroupButton.setImageResource(R.drawable.baseline_person_add_disabled_white_24dp)
                    // Update State
                    groupState.state = State.OUTSIDE
                }else{
                    // Change de FloatActionButton icon
                    actionGroupButton.setImageResource(R.drawable.baseline_group_add_white_24dp)
                    // Update State
                    groupState.state = State.INSIDE
                }
            }
            instantChange = false
        }

        subjectFieldTextEdit_ActGroup.addTextChangedListener{
            instantChange = true
        }

        locationFieldTextEdit_ActGroup.addTextChangedListener {
            instantChange = true
        }

        descriptionFieldTextEdit_ActGroup.addTextChangedListener {
            instantChange = true
        }

        // Add content FloatActionButton listener
        addContentFAB_ActGroup.setOnClickListener {
            //Put A dummy content
            recyclerViewAdapter.addContent(Content(0,"",""))
        }

        // Hide add content FloatActionButton
        addContentFAB_ActGroup.hide()

        // Hide Save hide
        saveFAB_ActGroup.hide()

        // ContentRecycleView
        contentRecyclerView = findViewById(R.id.content_recycler_view)
        recyclerViewAdapter = ContentRecyclerViewAdapter(contents, this, groupState)
        contentRecyclerView.adapter = recyclerViewAdapter
        contentTouchHelper = ItemTouchHelper(ContentSwipeToDeleteCallback(recyclerViewAdapter))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val buttonId = item?.itemId
        // Topbar (toolbar) back arrow listener
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
            if(groupState.state == State.EDIT){
                // Update state
                groupState.state = State.VIEW
                // Change textEdit to textView of static fields
                changeState(View.VISIBLE,  View.INVISIBLE)
                // Update FAB Button Icon
                actionGroupButton.setImageResource(R.drawable.baseline_edit_white_24dp)
                // Disable contentRecyclerView slide listener
                contentTouchHelper.attachToRecyclerView(null)
                // Hide add content FloatActionButton
                addContentFAB_ActGroup.hide()
                // Restore the changes on content
                recyclerViewAdapter.restore()
                instantChange = false
                if(infoChange){
                    saveFAB_ActGroup.show()
                }
            }else{
                val replyIntent = Intent()
                setResult(Activity.RESULT_CANCELED, replyIntent)
                finish()
            }
        }else{
            val replyIntent = Intent()
            if(groupState.state == State.INSIDE){
                replyIntent.putExtra("reply-type", 1)
                setResult(Activity.RESULT_OK, replyIntent)
            }else{
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }
            finish() // close this activity and return to preview activity (if there is any)
        }
    }

    // Change visibility of fields
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
        descriptionFieldTextView_ActGroup.text = group?.detail


        val initDate: Date = defDateFormat.parse(group?.data_init!!.split(" ")[0])
        initDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(initDate)
        //Utils.showAlertDialog(this, group?.data_init!!.split(" ")[0], initDate.toString())

        val endDate: Date = defDateFormat.parse(group?.data_end!!.split(" ")[0])
        endDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(endDate)

        initTimeTextView_ActGroup.text = group?.data_init!!.split(" ")[1]
        endTimeTextView_ActGroup.text = group?.data_end!!.split(" ")[1]
    }

    // Data Picker dialog reply callback
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        if(dialogCaller == Caller.DATE_INIT){
            curInitDate = defDateFormat.format(calendar.time)
            initDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(calendar.time)
        }else {
            curEndDate = defDateFormat.format(calendar.time)
            endDayTextView_ActGroup.text = DateFormat.getDateInstance()?.format(calendar.time)
        }
        instantChange = true
    }
    // Time Picker dialog reply callback
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val text = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        if(dialogCaller == Caller.TIME_INIT){
            initTimeTextView_ActGroup.text = text
        }else {
            endTimeTextView_ActGroup.text = text
        }
        instantChange = true
    }

    // Class to share state between this Activity and ContentRecyclerViewAdapter
    inner class GroupState(var state: State)
}
