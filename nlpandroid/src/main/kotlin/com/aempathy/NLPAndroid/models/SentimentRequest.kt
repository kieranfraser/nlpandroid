package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class SentimentRequest (@SerializedName("text") val text: String)