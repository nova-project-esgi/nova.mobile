package com.esgi.nova.languages.infrastructure.api

import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.languages.ports.IDefaultLanguage
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import retrofit2.Retrofit
import javax.inject.Inject

class LanguageApiRepository @Inject constructor(getUserToken: GetUserToken, updateUserToken: UpdateUserToken): ApiRepository(
    getUserToken,updateUserToken

) {

    private var languagesServices: LanguageService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()
        languagesServices = retrofit.create(LanguageService::class.java)
    }

    fun getAll(): List<IDefaultLanguage> {
        return languagesServices.getAll().execute().body() ?: listOf()
    }
}

