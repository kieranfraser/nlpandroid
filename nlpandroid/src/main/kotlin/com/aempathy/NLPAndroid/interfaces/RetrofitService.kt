package com.aempathy.NLPAndroid.interfaces

import com.aempathy.NLPAndroid.models.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/topic")
    fun getTopic(@Body body: TopicRequest): Deferred<Response<TopicResponse>>

    @POST("/ner")
    fun getNamedEntities(@Body body: NERRequest): Deferred<Response<NERResponse>>

    @POST("/sentiment")
    fun getSentiment(@Body body: SentimentRequest): Deferred<Response<SentimentResponse>>
}