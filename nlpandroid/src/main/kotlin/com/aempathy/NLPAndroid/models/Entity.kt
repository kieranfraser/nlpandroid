package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class Entity (
    @SerializedName("entity") val value: String,
    @SerializedName("type") val type: String
)