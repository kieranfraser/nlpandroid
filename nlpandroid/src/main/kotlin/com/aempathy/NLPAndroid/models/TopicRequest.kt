package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class TopicRequest (@SerializedName("text") val text: String)