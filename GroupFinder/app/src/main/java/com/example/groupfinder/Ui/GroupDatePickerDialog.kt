package com.example.groupfinder.Ui

import android.app.DatePickerDialog
import android.app.Dialog
import java.util.Calendar
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class GroupDatePickerDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        return DatePickerDialog(activity, activity as DatePickerDialog.OnDateSetListener,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
}