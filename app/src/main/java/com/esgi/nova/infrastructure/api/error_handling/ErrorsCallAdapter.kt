package com.esgi.nova.infrastructure.api.error_handling

import retrofit2.Call
import retrofit2.CallAdapter

class ErrorsCallAdapter(
    private val delegateAdapter: CallAdapter<Any, Call<*>>
) : CallAdapter<Any, Call<*>> by delegateAdapter {

    override fun adapt(call: Call<Any>):Call<*> {
        return delegateAdapter.adapt(CallWithErrorHandling(call))
    }
}