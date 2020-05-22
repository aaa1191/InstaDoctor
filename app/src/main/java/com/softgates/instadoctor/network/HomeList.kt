package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class HomeList(

    @Json(name="id")
    var id: Int? = null,

    @Json(name="name")
    var name: String? = null,

    @Json(name="description")
    var description: String? = null
)