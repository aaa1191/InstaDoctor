package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class MyChildList(
    @Json(name="child_id")
    var child_id: String? = null,

    @Json(name="child_name")
    var child_name: String? = null,

    @Json(name="child_gender")
    var child_gender: String? = null,

    @Json(name="child_age_year")
    var child_age_year: String? = null,

    @Json(name="child_age_month")
    var child_age_month: String? = null

    )