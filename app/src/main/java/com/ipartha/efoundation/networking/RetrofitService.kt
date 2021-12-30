package com.ipartha.efoundation.networking

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


class RetrofitService {

    companion object {





        var logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String?) {
                Log.d("Partha", "Partha "+message)
            }

        }).setLevel(HttpLoggingInterceptor.Level.BASIC)

        private val okHttpClient = OkHttpClient.Builder().addInterceptor(logging)
            .build()



        private val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos")
            .client(okHttpClient)
            .build()

        //ToDo inject the base URL
         fun <S> createService(serviceClass : Class<S>) : S {
            return retrofit.create(serviceClass)
        }
    }
}

