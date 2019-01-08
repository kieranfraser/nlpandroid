package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class SentimentResponse (@SerializedName("inference") val inference: Double)