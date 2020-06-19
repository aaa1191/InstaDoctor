package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class AvailabilityList(

    @Json(name="av_id")
    var av_id: String? = null,

    @Json(name="doc_id")
    var doc_id: String? = null,

    @Json(name="start_time")
    var start_time: String? = null,

    @Json(name="end_time")
    var end_time: String? = null,


    @Json(name="active_days")
    var active_days: String? = null,


    @Json(name="date")
    var date: String? = null

)