package com.example.groupfinder.userinterfaces.group

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.enums.State
import com.example.groupfinder.userinterfaces.group.GroupActivity.GroupState
import kotlinx.android.synthetic.main.group_content.view.*

class ContentRecyclerViewAdapter(private var Values: MutableList<Content>, var context: Context,
         private var groupState: GroupState): RecyclerView.Adapter<ContentRecyclerViewAdapter.ViewHolder>(){
    private lateinit var undoList: MutableList<Content>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_content, parent, false)
        // Pass a custom listener to the viewHolder, saving listener allocation
        return ViewHolder(view, ContentEditTextListener())
    }

    override fun getItemCount(): Int = Values.size

    fun deleteContent(position: Int){
        Values.removeAt(position)
        notifyDataSetChanged()
    }

    fun doBackup(){
        undoList = Values.toMutableList()
    }

    fun restore(){
        Values = undoList
        notifyDataSetChanged()
    }

    fun reload(){
        notifyDataSetChanged()
    }

    fun addContent(content: Content){
        Values.add(0, content)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Update Holder position
        holder.ContentEditTextListener.updatePosition(holder.adapterPosition)

        // Update Text View/Edit based on state (of screen)
        if (groupState.state == State.EDIT){
            holder.contentTextView.visibility =  View.INVISIBLE
            holder.contentTextEdit.visibility = View.VISIBLE
            holder.contentTextEdit.setText(Values[holder.adapterPosition].description)
        }else{
            holder.contentTextView.visibility =  View.VISIBLE
            holder.contentTextEdit.visibility = View.INVISIBLE
            holder.contentTextView.text = Values[holder.adapterPosition].description
        }
    }

    inner class ViewHolder(itemView: View, var ContentEditTextListener: ContentEditTextListener)
        : RecyclerView.ViewHolder(itemView){
        // Get Text views of respective ViewHolder
        var contentTextView: TextView = itemView.contentTextView_GroupContentLayout
        var contentTextEdit: EditText = itemView.contentTextEdit_GroupContentLayout

        init {
            // Add the listener to the text edit view
            contentTextEdit.addTextChangedListener(ContentEditTextListener)
        }
    }

    // Custom listener
    inner class ContentEditTextListener : TextWatcher {
        private var position: Int = 0

        fun updatePosition(position: Int) {
            this.position = position
        }

        // Update Value Variable
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            Values[position].description = charSequence.toString()
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) { }
        override fun afterTextChanged(editable: Editable) { }
    }
}