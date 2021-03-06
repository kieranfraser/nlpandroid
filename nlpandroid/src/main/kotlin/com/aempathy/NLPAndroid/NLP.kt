package com.aempathy.NLPAndroid

import android.content.Context
import android.util.Log
import com.aempathy.NLPAndroid.interfaces.RetrofitService
import com.aempathy.NLPAndroid.utils.RetrofitFactory
import com.aempathy.NLPAndroid.utils.StateUtils
import org.json.JSONArray
import retrofit2.HttpException
import android.graphics.ColorSpace.Model
import android.R.attr.data
import com.aempathy.NLPAndroid.models.*
import info.debatty.java.stringsimilarity.JaroWinkler




class NLP(mContext: Context, mLocalOnly: Boolean){

    private val TAG = "NLPAndroid: "+NLP::class.java.simpleName

    private val LABELS = arrayOf("Adult", "Arts & Entertainment", "Autos & Vehicles",
        "Beauty & Fitness", "Books & Literature", "Business & Industrial",
        "Computers & Electronics", "Finance", "Food & Drink", "Games",
        "Health", "Hobbies & Leisure", "Home & Garden",
        "Internet & Telecom", "Jobs & Education", "Law & Government",
        "News", "Online Communities", "People & Society", "Pets & Animals",
        "Real Estate", "Reference", "Science", "Sensitive Subjects",
        "Shopping", "Sports", "Travel")

    private val context = mContext
    private val localOnly = mLocalOnly
    private val service: RetrofitService?

    init {
        if(!mLocalOnly) {
            service = RetrofitFactory.makeRetrofitService()
        }
        else{
            service = null
        }
    }

    /**
     * Send request to Empushy-NLP server for classification of text topic
     * - if localOnly, local model used (less intelligent)
     * - if no network, 'unknown' returned
     */
    suspend fun classifyTopic(text: String): String {
        var topic = "unknown"
        if(!localOnly) {
            if(StateUtils.isNetworkAvailable(context)) {
                try {
                    val request = service?.getTopic(TopicRequest(text))
                    val response = request?.await()
                    topic = (response?.body() as TopicResponse).inference
                } catch (e: HttpException) {
                    Log.d(TAG, "" + e.code())
                } catch (e: Throwable) {
                    Log.d(TAG, "Ooops: Something else went wrong: " + e.message)
                }
            }
        }
        return topic
    }

    /**
     * Send request to Empushy-NLP server for extraction of Named Entities
     * - if localOnly, local model used (less intelligent)
     * - if no network, 'unknown' returned
     */
    suspend fun namedEntityRecognition(text: String): ArrayList<Entity> {
        var entities = mutableListOf<Entity>()
        if(!localOnly) {
            if(StateUtils.isNetworkAvailable(context)) {
                try {
                    val request = service?.getNamedEntities(NERRequest(text))
                    val response = request?.await()
                    val nerResponse = (response?.body() as NERResponse)
                    Log.d(TAG, nerResponse.toString())
                    entities = ArrayList(nerResponse.inference)
                    /*val jsonArray = (response?.body() as NERResponse).inference
                    for (i in 0..(jsonArray.length() - 1)) {
                        val item = jsonArray.getJSONObject(i)
                        val keys = item.keys()
                        while(keys.hasNext()){
                            val value:String = keys.next()
                            entities.add(Entity(value, item.getString(value)))
                        }
                    }*/
                } catch (e: HttpException) {
                    Log.d(TAG, "" + e.code())
                } catch (e: Throwable) {
                    Log.d(TAG, "Ooops: Something else went wrong: " + e.message)
                }
            }
        }
        return ArrayList(entities)
    }

    /**
     * Send request to Empushy-NLP server for classification of sentiment
     * - if localOnly, local model used (less intelligent)
     * - if no network, -1 returned
     */
    suspend fun classifySentiment(text: String): Double {
        var sentiment = -1.0
        if(!localOnly) {
            if(StateUtils.isNetworkAvailable(context)) {
                try {
                    val request = service?.getSentiment(SentimentRequest(text))
                    val response = request?.await()
                    sentiment = (response?.body() as SentimentResponse).inference
                } catch (e: HttpException) {
                    Log.d(TAG, "" + e.code())
                } catch (e: Throwable) {
                    Log.d(TAG, "Ooops: Something else went wrong: " + e.message)
                }
            }
        }
        return sentiment
    }

    fun matchEntityToContacts(personEntity:String, contactNames:List<String>, threshold: Double): MutableList<String> {
        val jw = JaroWinkler()
        val matchedContacts = mutableListOf<String>()
        for(contactName in contactNames){
            if(jw.similarity(personEntity, contactName) > threshold)
                matchedContacts.add(contactName)
        }
        return matchedContacts
    }
}
