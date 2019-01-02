package com.aempathy.NLPAndroid.interfaces

import com.aempathy.NLPAndroid.models.TopicRequest
import com.aempathy.NLPAndroid.models.TopicResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/topic")
    fun getTopic(@Body body: TopicRequest): Deferred<Response<TopicResponse>>
}