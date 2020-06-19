package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class PrescriptionList(
    @Json(name="app_id")
    var app_id: String? = null,

    @Json(name="medicine_name")
    var medicine_name: String? = null,

    @Json(name="medicine_dosage")
    var medicine_dosage: String? = null,

    @Json(name="medicine_type")
    var medicine_type: String? = null,

    @Json(name="course_period")
    var course_period: String? = null,

    @Json(name="serving")
    var serving: String? = null,

    @Json(name="comments")
    var comments: String? = null,

    @Json(name="doc_name")
    var doc_name: String? = null,

    @Json(name="pre_date")
    var pre_date: String? = null,

    @Json(name="visibility")
    var visibility: Int? = 0
)