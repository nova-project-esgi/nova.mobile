package com.esgi.nova.languages.infrastructure.api

import com.esgi.nova.infrastructure.api.apiBuilder
import retrofit2.Retrofit
import javax.inject.Inject

class LanguageApiRepository @Inject constructor() {

    private var languagesServices: LanguageService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        languagesServices = retrofit.create(LanguageService::class.java)
    }

    fun getAll(): List<LanguageResponse> {
        return languagesServices.getAll().execute().body() ?: listOf()
    }
}

