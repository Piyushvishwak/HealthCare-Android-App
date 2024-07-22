package com.example.traveldiary.network

import com.example.traveldiary.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("?results=10")
    fun getUsers(): Call<UserResponse>
}
