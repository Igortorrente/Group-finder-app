package com.example.groupfinder.base_classes

// Makes and handles calls to the API Service 
class ApiHandler {

    // Function that handles authentication to the server
    fun userAuth(ra: Int, senha: String): Void {
        val call = RetrofitInitializer().apiService().userAuth(ra, senha)
        
        call.enqueue(object: Callback<String?>) {
            override fun onResponse(call: Call<String?>?, response: Response<String?>?) {
                Int responseCode = response.code()
            
                if (responseCode == 200) {
                    // Handle sucessful login somehow
                }
                else if (responseCode == 404) {
                    // Handle inexistent user somehow
                }
                else if (responseCode == 403) {
                    // Handle wrong pwd somehow
                }
                else {
                    // Handle UNEXPECTED response somehow
                }
                
            }
            
            override fun onFailure(call: Call<String?>?, t: Throwable?) {
                // Handle random (or not) exceptions
            }
        }
        
    }

}