package com.esgi.nova.difficulties.application

import com.esgi.nova.difficulties.infrastructure.api.DifficultyRepository
import com.esgi.nova.difficulties.infrastructure.dto.TranslatedDifficultyDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SynchronizeDifficultiesToLocalStorage {

    fun execute() {
        DifficultyRepository.getAllTranslatedDifficulties(object :
        Callback<List<TranslatedDifficultyDto>> {
            override fun onResponse(
                call: Call<List<TranslatedDifficultyDto>>,
                response: Response<List<TranslatedDifficultyDto>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        println(it)
                    }
                } else {
                    println("no")
                }
            }

            override fun onFailure(call: Call<List<TranslatedDifficultyDto>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        } )
    }
}