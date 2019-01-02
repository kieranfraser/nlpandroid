package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class TopicResponse (@SerializedName("inference") val inference: String)