package com.example.groupfinder.userinterfaces.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.groupfinder.Data.Prefs
import com.example.groupfinder.Data.api.API
import com.example.groupfinder.Data.api.ApiHandler
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.R
import com.example.groupfinder.userinterfaces.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A login screen that offers login via email/senha.
 */
class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener { attemptLogin() }

        email_reg_button.setOnClickListener {
                view ->
            val intent = Intent(view.context, RegisterActivity::class.java)
            view.context.startActivity(intent)
        }
    }

    // Ensure that MainActivity gets shown, in case user, somehow, gets on this screen after registering
    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
        val result =  super.onCreateView(name, context, attrs)

        if (Prefs(context!!).userRa > 0) {
            val intent = Intent(context!!, MainActivity::class.java)
            context!!.startActivity(intent)
        }

        return result
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        user_ra.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val userRA = Integer(user_ra.text.toString())
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid senha, if the User entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (!(userRA > 0 && userRA <= 999999)) {
            user_ra.error = getString(R.string.error_field_required)
            focusView = user_ra
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the User login attempt.
            showProgress(true)

            GlobalScope.launch {

                val newUser = UserData(userRA.toInt(), "", "", API.getSHA512hash(passwordStr))
                val userAuthResponseDef = ApiHandler.userAuth(newUser)

                withContext(Dispatchers.Main) {
                    try {
                        val userAuthResponse = userAuthResponseDef.await()
                        val responseCode = userAuthResponse.code()

                        when(responseCode) {
                            200 -> {
                                showProgress(false)

                                Prefs(this@LoginActivity).userRa = userRA.toInt()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                this@LoginActivity.startActivity(intent)

                            }
                            403 -> {
                                showProgress(false)
                                API.showAlertDialog(this@LoginActivity, "Failed to Login", "The provided user RA and password didn't match")
                            }
                            404 -> {
                                showProgress(false)
                                API.showAlertDialog(this@LoginActivity, "Failed to Login", "The provided user RA isn't registered")
                            }
                            else -> {
                                showProgress(false)
                                API.showAlertDialog(this@LoginActivity, "Failed to Login", "An unknown error occurred while attempting to register")
                            }
                        }

                    }
                    catch (t: Throwable) {
                        showProgress(false)
                        API.showAlertDialog(this@LoginActivity, "Failed to Login", t.localizedMessage)
                    }
                }
            }

        }
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            login_form.visibility = if (show) View.GONE else View.VISIBLE
            login_form.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 0 else 1).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_form.visibility = if (show) View.GONE else View.VISIBLE
                    }
                })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                .setDuration(shortAnimTime)
                .alpha((if (show) 1 else 0).toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
                    }
                })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the User.
     */
    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) :
        AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            return DUMMY_CREDENTIALS
                .map { it.split(":") }
                .firstOrNull { it[0] == mEmail }
                ?.let {
                    // Account exists, return true if the senha matches.
                    it[1] == mPassword
                }
                ?: true
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            showProgress(false)

            if (success!!) {
                finish()
            } else {
                password.error = getString(R.string.error_incorrect_password)
                password.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known User names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}
