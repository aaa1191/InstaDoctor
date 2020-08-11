package com.softgates.instadoctor.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateAppointmentModel(
    @Json(name="status")
    var status: Int? = null,

    @Json(name="message")
    var message: String? = null,



    @Json(name="data")
    var data: MutableList<CreateAppointmentList>? = null
)