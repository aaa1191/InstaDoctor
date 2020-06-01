package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class GetDoctorDataModel(

    @Json(name="status")
    var status: Int? = null,

    @Json(name="message")
    var message: String? = null,

    @Json(name="otp_code")
    var otp_code: String? = null,

    @Json(name="data")
    var data: MutableList<DoctorList>? = null
)