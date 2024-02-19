package com.example.homework21

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.create

class Repository (private val apiClient: Retrofit){
    suspend fun getHeroes2(): Single<List<Hero>>? {
    val apiInterface = apiClient.create(ApiInterface::class.java)
    return apiInterface.getHeroes()
    }

}