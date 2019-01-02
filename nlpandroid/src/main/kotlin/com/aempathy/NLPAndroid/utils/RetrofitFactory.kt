package com.aempathy.NLPAndroid.utils

import com.aempathy.NLPAndroid.interfaces.RetrofitService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitFactory {
    const val BASE_URL = "https://empushy-nlp.azurewebsites.net"

    fun makeRetrofitService(): RetrofitService {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(RetrofitService::class.java)
    }
}