package com.example.homework21

import retrofit2.http.GET

interface ApiInterface {
    @GET("/superhero-api/api/all.json")
    suspend fun getHeroes(): List<Hero>?
}