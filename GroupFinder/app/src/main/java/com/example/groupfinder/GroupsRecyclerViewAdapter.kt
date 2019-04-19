package com.example.groupfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.base_classes.groupItem
import com.example.groupfinder.groupsFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_groups_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [groupItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GroupsRecyclerViewAdapter(
    private val mValues: List<groupItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as groupItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_groups_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mainSubjectView.text = item.mainSubject
        holder.placeView.text = item.place
        holder.imageView.setImageResource(item.image)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mainSubjectView: TextView = mView.groupMainSubjectTextView
        val placeView: TextView = mView.timeToBeginTextView
        val imageView: ImageView = mView.groupImageView

        override fun toString(): String {
            return super.toString() + " '" + placeView.text + "'"
        }
    }
}
