package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class Data(
    @Json(name="patient_id")
    var patient_id: Int? = null,



    @Json(name="patient_email")
    var patient_email: String? = null,

    @Json(name="token")
    var token: String? = null
)