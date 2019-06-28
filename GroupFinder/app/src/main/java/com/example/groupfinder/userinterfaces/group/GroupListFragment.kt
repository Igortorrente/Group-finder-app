package com.example.groupfinder.userinterfaces.group

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.enums.RequestCode
import com.example.groupfinder.viewmodels.FinderViewModel
import kotlinx.android.synthetic.main.fragment_groups_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GroupListFragment.OnListFragmentInteractionListener] interface.
 */
class GroupListFragment : Fragment() {
    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private val newGroupRequestCode = RequestCode.NEW_GROUP.number
    private val groupRequestCode = RequestCode.GROUP.number
    private lateinit var viewModel: FinderViewModel
    private lateinit var userGroups: LiveData<List<UserGroups>>
    private lateinit var listAdapter: GroupsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FinderViewModel::class.java)
        }!!

        viewModel.changeContext(this.context!!)

        userGroups = viewModel.userGroups
        Log.d("wtf","observers" + userGroups.hasObservers().toString())
        userGroups.observe(this, Observer { newList ->
            listAdapter.newItemAllert(newList)
            Log.d("wtf","Frag: " + userGroups.value!!.size.toString())
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_groups_list, container, false)
        val groupRecyclerView = view.findViewById<RecyclerView>(R.id.groups_recycler_view)
        // Set the adapter
        if (groupRecyclerView is RecyclerView) {
            with(groupRecyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                listAdapter = if(userGroups.value != null){
                    GroupsRecyclerViewAdapter(userGroups.value!!, listener, activity!!, viewModel)
                }else{
                    GroupsRecyclerViewAdapter(emptyList(), listener, activity!!, viewModel)
                }
                adapter = listAdapter
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            //throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        userGroups.removeObservers(this)
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addNewGoupFAB_FragGroups.setOnClickListener {
            val intent = Intent(activity, NewGroupActivity::class.java)
            startActivityForResult(intent, newGroupRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(activity, "Request Code: $requestCode", Toast.LENGTH_LONG).show()
        if(requestCode == newGroupRequestCode){
            if(resultCode == Activity.RESULT_OK){
                data?.let { returnedData ->
                    val group = returnedData.extras?.getParcelable("reply-user-info") as UserGroups
                    Log.d("intent-user", group.toString())
                    viewModel.insertGroup(group)
                }
                Toast.makeText(activity, "Sucesso !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Nenhuma mudança", Toast.LENGTH_LONG).show()
            }
        }
        else if (requestCode == groupRequestCode) {
            if(resultCode == Activity.RESULT_OK){
                data?.let { returnedData ->
                    val group = returnedData.extras?.getParcelable("reply-groupArg-info") as UserGroups
                    Log.d("intent-user", group.toString())
                    viewModel.updateGroup(group)
                }
            } else {
                Toast.makeText(activity, "Nenhuma mudança", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: UserGroups?)
    }
}
