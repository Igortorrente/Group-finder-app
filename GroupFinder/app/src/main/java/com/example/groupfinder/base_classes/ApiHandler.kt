package com.example.groupfinder.base_classes

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Makes and handles calls to the API Service 
class ApiHandler {

    // Function that handles authentication to the server
    fun userAuth(ra: Int, senha: String) {
        val call = RetrofitInitializer().apiService().userAuth(ra, senha)
        
        call.enqueue(object: Callback<String?> {
            override fun onResponse(call: Call<String?>?, response: Response<String?>?) {
                val responseCode = response?.code()

                when (responseCode) {
                    200 -> {
                        // Handle sucessful login somehow
                    }
                    404 -> {
                        // Handle inexistent user somehow
                    }
                    403 -> {
                        // Handle wrong pwd somehow
                    }
                    else -> {
                        // Handle UNEXPECTED response somehow
                    }
                }
                
            }
            
            override fun onFailure(call: Call<String?>?, t: Throwable?) {
                // Handle random (or not) exceptions
            }
        })
        
    }

}
