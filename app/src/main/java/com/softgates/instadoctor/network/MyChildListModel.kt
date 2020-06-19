package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class MyChildListModel(

    @Json(name="status")
    var status: Int? = null,

    @Json(name="message")
    var message: String? = null,

    @Json(name="data")
    var data: MutableList<MyChildList>? = null
)