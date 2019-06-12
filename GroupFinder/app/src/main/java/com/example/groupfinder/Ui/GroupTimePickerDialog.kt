package com.example.groupfinder.Ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class GroupTimePickerDialog : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener, 0, 0, true)
    }
}