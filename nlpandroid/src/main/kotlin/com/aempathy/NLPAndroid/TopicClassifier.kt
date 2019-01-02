package com.aempathy.NLPAndroid

import android.content.Context
import android.util.Log
import com.aempathy.NLPAndroid.interfaces.RetrofitService
import com.aempathy.NLPAndroid.models.TopicRequest
import com.aempathy.NLPAndroid.utils.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.HttpException


class TopicClassifier(mContext: Context){

    private val TAG = "NLPAndroid: "+TopicClassifier::class.java.simpleName

    private val LABELS = arrayOf("Adult", "Arts & Entertainment", "Autos & Vehicles",
        "Beauty & Fitness", "Books & Literature", "Business & Industrial",
        "Computers & Electronics", "Finance", "Food & Drink", "Games",
        "Health", "Hobbies & Leisure", "Home & Garden",
        "Internet & Telecom", "Jobs & Education", "Law & Government",
        "News", "Online Communities", "People & Society", "Pets & Animals",
        "Real Estate", "Reference", "Science", "Sensitive Subjects",
        "Shopping", "Sports", "Travel")

    private val context = mContext
    private val service: RetrofitService

    init {
        service = RetrofitFactory.makeRetrofitService()
    }

    suspend fun classifyTopic(text: String): String {
        var topic = "unknown"
        val request = service.getTopic(TopicRequest("This is my ultra cool smartphone."))
        try {
            val response = request.await()
            topic = response.body()?:"unknown"
            Log.d(TAG, topic)
        } catch (e: HttpException) {
            Log.d(TAG, ""+e.code())
        } catch (e: Throwable) {
            Log.d(TAG, "Ooops: Something else went wrong")
        }
        return topic
    }
}
