package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class PrescriptionList(
    @Json(name="app_id")
    var app_id: String? = "",

    @Json(name="medicine_name")
    var medicine_name: String? = "",

    @Json(name="medicine_dosage")
    var medicine_dosage: String? = "",

    @Json(name="medicine_type")
    var medicine_type: String? = "",

    @Json(name="course_period")
    var course_period: String? = "",

    @Json(name="serving")
    var serving: String? = "",

    @Json(name="comments")
    var comments: String? = "",

    @Json(name="doc_name")
    var doc_name: String? = "",

    @Json(name="pre_date")
    var pre_date: String? = "",

    @Json(name="visibility")
    var visibility: Int? = 0
)