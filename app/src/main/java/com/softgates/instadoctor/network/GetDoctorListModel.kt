package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class GetDoctorListModel(

    @Json(name="online_doctor_array")
    var online_doctor_array: MutableList<DoctorList>? = null,

    @Json(name="offline_doctor_array")
    var offline_doctor_array: MutableList<DoctorList>? = null

)