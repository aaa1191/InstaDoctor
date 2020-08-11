package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class Data(

    @Json(name="patient_id")
    var patient_id: Int? = null,

    @Json(name="patient_name")
    var patient_name: String? = "",

    @Json(name="patient_gender")
    var patient_gender: String? = "",

    @Json(name="patient_email")
    var patient_email: String? = null,

    @Json(name="child_id")
    var child_id: Int? = null,

    @Json(name="token")
    var token: String? = null,

    @Json(name="phone")
    var phone: String? = "",

    @Json(name="address")
    var address: String? = "",

    @Json(name="address2")
    var address2: String? = "",

    @Json(name="city")
    var city: String? = "",

    @Json(name="state")
    var state: String? = "",

    @Json(name="zipcode")
    var zipcode: String? = "",

    @Json(name="no_of_child")
    var no_of_child: Int? = 0
)