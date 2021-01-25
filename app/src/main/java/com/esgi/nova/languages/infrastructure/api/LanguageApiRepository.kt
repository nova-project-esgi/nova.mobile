package com.esgi.nova.languages.infrastructure.api

import android.content.Context
import com.esgi.nova.files.infrastructure.api.FileService
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.languages.ports.IDefaultLanguage
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Inject

class LanguageApiRepository @Inject constructor(
    @ApplicationContext context: Context,
    getUserToken: GetUserToken, updateUserToken: UpdateUserToken
) : AuthenticatedApiRepository(
    getUserToken, updateUserToken, context

) {

    private var languagesServices: LanguageService = apiBuilder()
        .build()
        .create(LanguageService::class.java)

    fun getAll(): List<IDefaultLanguage> {
        return languagesServices.getAll().execute().body() ?: listOf()
    }
}

