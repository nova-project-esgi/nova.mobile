package com.esgi.nova.languages.infrastructure.api

import com.esgi.nova.infrastructure.api.apiBuilder
import com.esgi.nova.languages.infrastructure.dto.LanguageDto
import retrofit2.Retrofit
import javax.inject.Inject

class LanguageApiRepository @Inject constructor() {

    private var languagesRequests: LanguagesRequests

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        languagesRequests = retrofit.create(LanguagesRequests::class.java)
    }

    fun getAll(): List<LanguageDto> {
        return languagesRequests.getAll()?.execute()?.body() ?: listOf()
    }
}

