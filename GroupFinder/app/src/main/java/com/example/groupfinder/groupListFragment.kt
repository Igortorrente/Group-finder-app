package com.example.groupfinder

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.base_classes.API
import com.example.groupfinder.base_classes.ApiHandler
import com.example.groupfinder.base_classes.UserMeetings
import kotlinx.coroutines.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [groupListFragment.OnListFragmentInteractionListener] interface.
 */
class groupListFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                GlobalScope.launch {
                    val groupsListDef = ApiHandler().userGroups(177953)

                        withContext(Dispatchers.Main) {
                            try {
                                val groupsListResponse = groupsListDef.await()

                                when {
                                    groupsListResponse.code() == 404 -> {
                                        val dialog = AlertDialog.Builder(view.context)
                                            .setTitle("User Not Found")
                                            .setMessage("The given user was not found")
                                            .setNeutralButton("Yes") {
                                                dialog, which ->
                                                dialog.cancel()
                                            }
                                            .create()

                                        dialog.show()
                                    }
                                    groupsListResponse.code() == 200 -> {
                                        groupsListResponse.body()?.let {
                                            if (it.isEmpty()) {
                                                val dialog = AlertDialog.Builder(view.context)
                                                    .setTitle("No Groups Found")
                                                    .setMessage("This user hasn't joined any group")
                                                    .setNeutralButton("Yes") {
                                                            dialog, which ->
                                                        dialog.cancel()
                                                    }
                                                    .create()

                                                dialog.show()
                                            }
                                            else {
                                                adapter = GroupsRecyclerViewAdapter(it, listener)
                                            }
                                        }
                                    }
                                    else -> {

                                    }
                                }
                            }
                            catch (t: Throwable) {
                                val dialog = AlertDialog.Builder(view.context)
                                    .setTitle("Failed to connect")
                                    .setMessage("Failed to connect to external server\n" + t.message)
                                    .setNeutralButton("Yes") {
                                            dialog, which ->
                                        dialog.cancel()
                                    }
                                    .create()

                                dialog.show()
                            }
                        }

                        //adapter = GroupsRecyclerViewAdapter(API.getUserGroups("RESPONSE " + groupsList.code()), listener)
                        //Toast.makeText(view.context, "RESPONSE " + groupsList.code(), Toast.LENGTH_LONG)

                        //Toast.makeText(view.context, t.message, Toast.LENGTH_LONG)
                }



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
        listener = null
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
        // TODO: Update argument type and nome
        fun onListFragmentInteraction(item: UserMeetings?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            groupListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
