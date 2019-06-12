package com.example.groupfinder.Ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.activity_new_group.*
import java.text.DateFormat
import java.util.*

enum class Caller { TIME_INIT, TIME_END, DATE_INIT, DATE_END }

class NewGroupActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private var dialogCaller = Caller.TIME_INIT
    private val datePicker = GroupDatePickerDialog()
    private val timePicker = GroupTimePickerDialog()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_group)

        // add back arrow to toolbar
        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        addGroupFAB.setOnClickListener {

        }

        initDayTextView.setOnClickListener {
            datePicker.show(supportFragmentManager, "init date picker")
            dialogCaller = Caller.DATE_INIT
        }

        endDayTextView.setOnClickListener {
            datePicker.show(supportFragmentManager, "end date picker")
            dialogCaller = Caller.DATE_END
        }

        initTimeTextView.setOnClickListener {
            timePicker.show(supportFragmentManager, "init hour picker")
            dialogCaller = Caller.TIME_INIT
        }

        endTimeTextView.setOnClickListener {
            timePicker.show(supportFragmentManager, "end hour picker")
            dialogCaller = Caller.TIME_END
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        if(dialogCaller == Caller.DATE_INIT){
            initDayTextView.text = DateFormat.getDateInstance()?.format(calendar.time)
        }else {
            endDayTextView.text = DateFormat.getDateInstance()?.format(calendar.time)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val text = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        if(dialogCaller == Caller.TIME_INIT){
            initTimeTextView.text = text
        }else {
            endTimeTextView.text = text
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (item!!.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
