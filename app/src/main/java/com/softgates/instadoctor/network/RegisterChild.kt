package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class RegisterChild(
    @Json(name="child_id")
    var child_id: Int? = null,

    @Json(name="child_count")
    var child_count: Int? = null
)