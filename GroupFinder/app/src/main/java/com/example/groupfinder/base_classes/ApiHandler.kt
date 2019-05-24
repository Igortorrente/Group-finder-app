package com.example.groupfinder.base_classes

import android.content.Context
import android.widget.Toast
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiUser(val ra: Int, val nome: String? = "", val curso: String? = "", val senha:String)
class ApiGroup()

// Makes and handles calls to the API Service 
class ApiHandler {

    private lateinit var context: Context

    // Function that handles authentication to the server
    fun userAuth(user: ApiUser) {
        val call = RetrofitInitializer().apiService().userAuth(user)
        
        call.enqueue(object: Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject?>?, response: Response<JsonObject?>?) {
                val responseCode = response?.code()

                when (responseCode) {
                    200 -> {
                        // TODO: Implement proper code for handling successful login
                        // (store student RA in database and start main activity?)
                        Toast.makeText(context, "200 OK", Toast.LENGTH_LONG).show()
                    }
                    404 -> {
                        // Handle inexistent user somehow
                        Toast.makeText(context, "404 ERROR", Toast.LENGTH_LONG).show()
                    }
                    403 -> {
                        Toast.makeText(context, "403 ERROR", Toast.LENGTH_LONG).show()
                        // Handle wrong pwd somehow
                    }
                    else -> {
                        Toast.makeText(context, "UNEXPECTED ERROR", Toast.LENGTH_LONG).show()
                        // Handle UNEXPECTED response somehow
                    }
                }
                
            }
            
            override fun onFailure(call: Call<JsonObject?>?, t: Throwable?) {
                Toast.makeText(context, t?.message, Toast.LENGTH_LONG).show()
                // Handle random (or not) exceptions
            }
        })
        
    }

    fun setContext(con: Context) {
        this.context = con
    }

}
