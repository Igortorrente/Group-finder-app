package com.example.groupfinder.userinterfaces.group

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.enums.RequestCode
import com.example.groupfinder.userinterfaces.enums.UserState
import com.example.groupfinder.userinterfaces.group.GroupListFragment.OnListFragmentInteractionListener
import com.example.groupfinder.viewmodels.FinderViewModel
import kotlinx.android.synthetic.main.fragment_groups_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [groupItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GroupsRecyclerViewAdapter(
    private var mValues: List<UserGroups>,
    private val mListener: OnListFragmentInteractionListener?,
    private val activity: FragmentActivity,
    private val viewModel: FinderViewModel
) : RecyclerView.Adapter<GroupsRecyclerViewAdapter.ViewHolder>() {
//    private val
    private val mOnClickListener: View.OnClickListener
    private val groupRequestCode = RequestCode.GROUP.number

    init {
        mOnClickListener = View.OnClickListener { view ->
            val item = view.tag as UserGroups
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
            val intent = Intent(view.context, GroupActivity::class.java)

            var state = UserState.OUTSIDE
            // TODO: dummy here
            if(item.user_creator == viewModel.getCurrentRA()){
                state = UserState.ADMIN
            } else{
                if(viewModel.userGroups.value != null && viewModel.userGroups.value!!.isNotEmpty()){
                    for (i in viewModel.userGroups.value!!.indices){
                        // TODO: dummy here
                        if(viewModel.userGroups.value!![i].id == item.id){
                            state = UserState.INSIDE
                            break
                        }
                    }
                }
            }
            intent.putExtra("state", state)

            intent.putExtra("groupArg-info", item)
            startActivityForResult(activity , intent, groupRequestCode, null)

        }
    }

    fun newItemAllert(newList: List<UserGroups>){
        this.mValues = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_groups_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mainSubjectView.text = item.subject
        holder.placeView.text = item.location_description
        holder.imageView.setImageResource(R.drawable.gde)

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
