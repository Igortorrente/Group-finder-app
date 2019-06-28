package com.example.groupfinder.userinterfaces.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import com.example.groupfinder.viewmodels.FinderViewModel
import kotlinx.android.synthetic.main.fragment_group_search_list.*


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GroupSearchFragment.OnListFragmentInteractionListener] interface.
 */
class GroupSearchFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var viewModel: FinderViewModel
    private lateinit var groupsToDisplay: LiveData<List<UserGroups>>
    private lateinit var listAdapter: GroupSearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FinderViewModel::class.java)
        }!!
        groupsToDisplay = viewModel.GroupsSearched

        groupsToDisplay.observe(this, Observer { newList ->
            listAdapter.searchAllert(newList)
        })
}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_group_search_list, container, false)
        val groupRecyclerView = view.findViewById<RecyclerView>(R.id.groups_search_recycler_view)

        // Set the adapter
        if (groupRecyclerView is RecyclerView) {
            with(groupRecyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                listAdapter = if(groupsToDisplay.value != null) {
                    GroupSearchRecyclerViewAdapter(groupsToDisplay.value!!, listener, activity!!, viewModel)
                } else{
                    GroupSearchRecyclerViewAdapter(emptyList(), listener, activity!!, viewModel)
                }
                adapter = listAdapter
            }
        }
        return view
    }


    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Implements the search listener
        searchView_FragSearchGroup.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchGroups(query)
                return false
            }

        })
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
        fun onListFragmentInteraction(item: UserGroups?)
    }
}
