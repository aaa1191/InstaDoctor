package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class SessionList(

    @Json(name="app_id")
    var app_id: String? = null,

    @Json(name="doc_name")
    var doc_name: String? = null,

    @Json(name="app_date")
    var app_date: String? = null,

    @Json(name="app_time")
    var app_time: String? = null,

    @Json(name="status")
    var status: String? = null
)