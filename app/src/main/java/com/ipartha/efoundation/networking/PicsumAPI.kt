package com.ipartha.efoundation.networking


import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PicsumAPI {
    @GET("/{WIDTH}/{HEIGHT}")
    suspend fun getPicsumPhoto(@Path("WIDTH")width: Int, @Path("HEIGHT") height: Int) : ResponseBody
}