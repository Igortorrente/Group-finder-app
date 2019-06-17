package com.example.groupfinder.userinterfaces.group

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.R
import kotlinx.android.synthetic.main.group_content.view.*


class ContentRecyclerViewAdapter(private var Values: MutableList<Content>, var context: Context)
    : RecyclerView.Adapter<ContentRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.group_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = Values.size

    fun deleteContent(position: Int){
        Values.removeAt(position)
        notifyDataSetChanged()
    }

    fun addContent(content: Content){
        Values.add(0, content)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Values[position]
        holder.contentText.text = item.description
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var contentText = itemView.contentTextView_GroupContentLayout
    }
}