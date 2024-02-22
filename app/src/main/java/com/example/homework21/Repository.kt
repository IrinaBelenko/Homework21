package com.example.homework21


import retrofit2.Retrofit

class Repository (private val apiClient: Retrofit){
    suspend fun getHeroes2(): List<Hero>? {
    val apiInterface = apiClient.create(ApiInterface::class.java)
    return apiInterface.getHeroes()
    }

}