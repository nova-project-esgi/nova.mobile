package com.esgi.nova.api

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {

    var token : String = "";

    fun Token(token: String ) {
        this.token = token;
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if(request.header("No-Authentication")==null){
            //val token = getTokenFromSharedPreference();
            //or use Token Function
            val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYW5kdWx1IiwiZXhwIjoxNjExMTc4NjgyfQ.-ru5ZwuHt5HQ6quFy3GBojByhPqjDDEqJkSFHpN5V1KgLg8m-0vNmP0YbJ61YVJZmYsERBPh7uiZ9LvUZ95hVw"
            if(!token.isNullOrEmpty())
            {
                val finalToken =  "Bearer "+token
                request = request.newBuilder()
                    .addHeader("Authorization",finalToken)
                    .build()
            }

        }

        return chain.proceed(request)
    }

}