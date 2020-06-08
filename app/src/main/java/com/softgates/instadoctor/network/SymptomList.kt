package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class SymptomList (
    @Json(name="id")
    var id: Int? = null,

    @Json(name="symptom_name")
    var symptom_name: String? = null,

    @Json(name="tick")
    var tick: Int? = 0
)