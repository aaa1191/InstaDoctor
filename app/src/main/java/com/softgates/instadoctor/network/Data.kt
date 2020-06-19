package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class Data(

    @Json(name="patient_id")
    var patient_id: Int? = null,

    @Json(name="patient_email")
    var patient_email: String? = null,

    @Json(name="child_id")
    var child_id: Int? = null,

    @Json(name="token")
    var token: String? = null,

    @Json(name="phone")
    var phone: String? = null,

    @Json(name="address")
    var address: String? = null,

    @Json(name="address2")
    var address2: String? = null,

    @Json(name="city")
    var city: String? = null,

    @Json(name="state")
    var state: String? = null,

    @Json(name="zipcode")
    var zipcode: String? = null
)