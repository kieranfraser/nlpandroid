package com.aempathy.NLPAndroid.models

import com.google.gson.annotations.SerializedName

data class Entity (
    @SerializedName("entity") val entity: String ?= "",
    @SerializedName("type") val type: String ?= ""
)