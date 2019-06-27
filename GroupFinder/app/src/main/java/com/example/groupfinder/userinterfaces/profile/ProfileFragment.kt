package com.example.groupfinder.userinterfaces.profile

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
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.enums.RequestCode
import com.example.groupfinder.viewmodels.FinderViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    private val profileRequestCode = RequestCode.PROFILE_EDIT.number
    private lateinit var viewModel: FinderViewModel
    private lateinit var userData: LiveData<UserData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(FinderViewModel::class.java)
        }!!

        userData = viewModel.userInfo

        userData.observe(this, Observer {
            userData.value?.let {
                nameFieldTextView_FragProfile.text = userData.value!!.name
                courseFieldTextView_FragProfile.text = userData.value!!.course
                RAFieldTextView_FragProfile.text = userData.value!!.ra.toString()
                Log.d("intent-user", userData.value.toString())
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userData.value?.let {
            nameFieldTextView_FragProfile.text = userData.value!!.name
            courseFieldTextView_FragProfile.text = userData.value!!.course
            RAFieldTextView_FragProfile.text = userData.value!!.ra.toString()
        }

        editProfileButton.setOnClickListener { view ->
            val intent = Intent(view.context, ProfileEditActivity::class.java)
            userData.value?.let {
                intent.putExtra("user-info", userData.value)
            }
            startActivityForResult(intent, profileRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == profileRequestCode){
            if(resultCode == Activity.RESULT_OK){
                data?.let { data ->
                    val userInfo = data.extras?.getParcelable("reply-user-info") as UserData
                    Log.d("intent-user", userInfo.toString())
                    nameFieldTextView_FragProfile.text = userInfo.name
                    courseFieldTextView_FragProfile.text = userInfo.course
                    RAFieldTextView_FragProfile.text = userInfo.ra.toString()

                    nameFieldTextView_FragProfile.refreshDrawableState()
                    courseFieldTextView_FragProfile.refreshDrawableState()
                    RAFieldTextView_FragProfile.refreshDrawableState()
                }
                Toast.makeText(this.context, "Sucesso !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, "Nenhuma mudança", Toast.LENGTH_LONG).show()
            }
        }
    }
}
