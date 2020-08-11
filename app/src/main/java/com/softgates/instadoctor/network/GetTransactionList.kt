package com.softgates.instadoctor.network

import com.squareup.moshi.Json

data class GetTransactionList(

@Json(name="status")
var status: Int? = null,

@Json(name="doc_id")
var doc_id: String? = null,

@Json(name="doc_name")
var doc_name: String? = null,

@Json(name="doc_fee")
var doc_fee: String? = null,

@Json(name="vat")
var vat: String? = null,

@Json(name="service_charges")
var service_charges: String? = null,

@Json(name="order_id")
var order_id: String? = null

)