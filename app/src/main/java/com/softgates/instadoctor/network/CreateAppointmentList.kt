package com.softgates.instadoctor.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.math.BigInteger


@JsonClass(generateAdapter = true)
data class CreateAppointmentList(
    @Json(name="id")
    var id: BigInteger? = null,


    @Json(name="password")
    var password: String? = null,

    @Json(name="topic")
    var topic: String? = null,

    @Json(name="start_time")
    var start_time: String? = null,

    @Json(name="duration")
    var duration: Int? = null,

    @Json(name="app_id")
    var app_id: String? = null

    )