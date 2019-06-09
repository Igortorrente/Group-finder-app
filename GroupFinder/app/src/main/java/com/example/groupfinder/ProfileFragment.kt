package com.example.groupfinder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.groupfinder.base_classes.Classes
import com.example.groupfinder.base_classes.FinderViewModel
import com.example.groupfinder.base_classes.UserData
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private val profileEditFragmentRequestCode = 1
    private lateinit var viewModel: FinderViewModel
    private lateinit var userData: LiveData<UserData>
    private lateinit var userClasses: LiveData<List<Classes>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FinderViewModel::class.java)
        }!!

        userData = viewModel.userInfo
        userClasses = viewModel.userClasses

        userData.observe(this, Observer {
            userData.value?.let {
                subjectFieldTextView.text = userData.value!!.name
                locationFieldTextView.text = userData.value!!.course
                dataInitFieldTextView.text = userData.value!!.ra.toString()
                Log.d("intent-user", userData.value.toString())
            }
        })

        userClasses.observe(this, Observer {
            //TODO: implement
        })
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userData.value?.let {
            subjectFieldTextView.text = userData.value!!.name
            locationFieldTextView.text = userData.value!!.course
            dataInitFieldTextView.text = userData.value!!.ra.toString()
        }

        editProfileButton.setOnClickListener { view ->
            val intent = Intent(view.context, ProfileEditActivity::class.java)
            userData.value?.let {
                intent.putExtra("userinfo", userData.value)
            }
            userClasses.value?.let {
                intent.putParcelableArrayListExtra("userclasses", userClasses.value as ArrayList<Classes>)
            }
            startActivityForResult(intent, profileEditFragmentRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == profileEditFragmentRequestCode){
            if(resultCode == Activity.RESULT_OK){
                data?.let { data ->
                    var userInfo = data.extras?.getParcelable("replyuserinfo") as UserData
                    Log.d("intent-user", userInfo.toString())
                    subjectFieldTextView.text = userInfo.name
                    locationFieldTextView.text = userInfo.course
                    dataInitFieldTextView.text = userInfo.ra.toString()

                    subjectFieldTextView.refreshDrawableState()
                    locationFieldTextView.refreshDrawableState()
                    dataInitFieldTextView.refreshDrawableState()
                    // TODO: add viewModel and user's Classes
                }
                Toast.makeText(this.context, "Sucesso !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, "Nenhuma mudança", Toast.LENGTH_LONG).show()
            }
        }
    }
}
