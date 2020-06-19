package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class MedicalHistoryModelList(

    @Json(name="session_details")
    var session_details: MutableList<SessionList>? = null,

    @Json(name="prescription_detail")
    var prescription_detail: MutableList<PrescriptionList>? = null
)