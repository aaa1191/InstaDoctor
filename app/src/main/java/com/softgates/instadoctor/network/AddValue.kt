package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class AddValue(
    @Json(name="addTxt")
    var addTxt: String? = null
)