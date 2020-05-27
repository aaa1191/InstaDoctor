package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class DoctorReviewList(

    @Json(name="patient_name")
    var patient_name: String? = null,

    @Json(name="review_stars")
    var review_stars: String? = null,

    @Json(name="review_description")
    var review_description: String? = null

)