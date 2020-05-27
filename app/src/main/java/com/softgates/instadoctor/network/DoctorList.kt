package com.softgates.instadoctor.network

import com.squareup.moshi.Json
import java.io.Serializable

data class DoctorList(

    @Json(name="id")
    var id: String? = null,

    @Json(name="doc_name")
    var doc_name: String? = null,

    @Json(name="doc_emirates_id")
    var doc_emirates_id: String? = null,


    @Json(name="doc_image_link")
    var doc_image_link: String? = null,


    @Json(name="doc_specialty")
    var doc_specialty: String? = null,

    @Json(name="doc_education")
    var doc_education: String? = null,

    @Json(name="doc_language")
    var doc_language: String? = null,

    @Json(name="doc_nationality")
    var doc_nationality: String? = null,

    @Json(name="doc_message")
    var doc_message: String? = null,

    @Json(name="doc_online_status")
    var doc_online_status: String? = null,

    @Json(name="hosp_id")
    var hosp_id: String? = null,

    @Json(name="doc_exp")
    var doc_exp: String? = null,

    @Json(name="total_reviews")
    var total_reviews: String? = null,

    @Json(name="avg_rating")
    var avg_rating: String? = null,

    @Json(name="total_patient_attended")
    var total_patient_attended: String? = null,

    @Json(name="today_timing")
    var today_timing: String? = null,

    @Json(name="doc_fee")
    var doc_fee: String? = null




): Serializable