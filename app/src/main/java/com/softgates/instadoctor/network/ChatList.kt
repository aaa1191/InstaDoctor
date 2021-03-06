package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class ChatList(
    @Json(name="id")
    var id: String? = null,

    @Json(name="english")
    var english: String? = null
)