package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class NERResponse (@SerializedName("inference") val inference: List<Entity>)