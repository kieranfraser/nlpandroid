package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class NERRequest (@SerializedName("text") val text: String)