package com.softgates.instadoctor.network

import com.squareup.moshi.Json
import java.io.Serializable

data class PropertiesImages(
    @Json(name="listing_image")
    var listing_image: String? = null
): Serializable