package com.softgates.instadoctor.util

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.softgates.instadoctor.network.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import retrofit2.http.GET
import java.util.concurrent.TimeUnit




//private const val BASE_URL = "http://18.218.168.60/hotshelf/api/"
private const val BASE_URL = "https://unclefluffy.com/instadoctor/test/assets/api/"
//private const val FINALBASE_URL = "https://flatnvilla.com/flat-villa/api/"

enum class ApiStatus { LOADING, ERROR, DONE }


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

/*private val finalretrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(FINALBASE_URL)
    .build()*/

/**
 * A public interface that exposes the [getProperties] method
 */
interface InstaDoctorApiService {

    @GET("category")
    fun getCategory(
    ): Deferred<ResponseModel>

    @GET("index.php")
    fun getDoctorList(
        @Query("apiname") apiname: String
    ): Deferred<GetDoctorDataModel>

    @GET("index.php")
    fun getChooseDoctorList(
        @Query("apiname") apiname: String,
        @Query("symptoms") device_type: String
    ): Deferred<GetDoctorDataModel>

    @GET("index.php")
    fun MyChildList(
        @Query("apiname") apiname: String,
        @Query("patient_id") device_type: String
    ): Deferred<MyChildListModel>


    @GET("index.php")
    fun availability(
        @Query("apiname") apiname: String,
        @Query("token") token: String,
        @Query("doc_id") doc_id: String
    ): Deferred<GetScheduleModel>


    @GET("index.php")
    fun MyPrescriptionList(
        @Query("apiname") apiname: String,
        @Query("app_id") device_type: String
    ): Deferred<PrescriptionListModel>

  /*  @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("apiname") apiname: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_id") device_id: String
    ): Deferred<ResponseModel>*/

    @FormUrlEncoded
    @POST("index.php")
    fun login(
        @Field("apiname") apiname: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role_id") type: String
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("index.php")
    fun addPatientDetail(
        @Field("apiname") apiname: String,
        @Field("token") token: String,
        @Field("patient_id") patientid: String,
        @Field("child_status") child_status: String,
        @Field("child_id") child_id: String,
        @Field("no_of_days") no_of_days: String,
        @Field("medication") medication: String,
        @Field("medication_names") medication_names: String,
        @Field("allergy") allergy: String,
        @Field("drug_allergy") drug_allergy: String,
        @Field("weight") weight: String,
        @Field("height") height: String,
        @Field("app_id") app_id: String
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("index.php")
    fun submitFeedback(
        @Field("apiname") apiname: String,
        @Field("token") token: String,
        @Field("patient_id") patientid: String,
        @Field("doc_id") doc_id: String,
        @Field("review_stars") review_stars: String,
        @Field("review_description") review_description: String,
        @Field("today_date") today_date: String
    ): Deferred<ResponseModel>

    @GET("index.php")
    fun forgetpassword(
        @Query("apiname") apiname: String,
        @Query("email") email: String
    ): Deferred<ResponseModel>

    @GET("index.php")
    fun contactinformation(
            @Query("apiname") apiname: String,
        @Query("token") token: String,
        @Query("old_email") old_email: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
        @Query("address") address: String,
        @Query("address2") address2: String,
        @Query("city") city: String,
        @Query("state") state: String,
        @Query("zipcode") zipcode: String,
        @Query("password") password: String
    ): Deferred<ResponseModel>

    @GET("index.php")
    fun registerChild(
        @Query("apiname") apiname: String,
        @Query("token") token: String,
        @Query("child_name") child_name: String,
        @Query("child_age_year") child_age_year: String,
        @Query("child_age_month") child_age_month: String,
        @Query("email") email: String,
        @Query("date") date: String,
        @Query("child_gender") child_gender: String
    ): Deferred<ResponseModel>

    @GET("index.php")
    fun doctorreview(
        @Query("apiname") apiname: String,
        @Query("doc_id") docid: String,
        @Query("token") token: String
    ): Deferred<DoctorReviewListModel>

    @GET("index.php")
    fun getSymptoms(
        @Query("apiname") apiname: String
    ): Deferred<GetSymptomListModel>

    @FormUrlEncoded
    @POST("index.php")
    fun changepassword(
        @Field("apiname") apiname: String,
        @Field("old_password") oldpassword: String,
        @Field("new_password") newpassword: String,
        @Field("email") email: String,
        @Field("token") token: String
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("index.php")
    fun register(
        @Field("apiname") apiname: String,
        @Field("patient_name") name: String,
        @Field("patient_email") email: String,
        @Field("patient_password") password: String,
        @Field("patient_gender") gender: String,
        @Field("patient_dob") dob: String,
        @Field("device_type") device_type: String,
        @Field("device_id") device_id: String,
        @Field("role_id") type: String
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("index.php")
    fun getMedicalHistory(
        @Field("apiname") apiname: String,
        @Field("token") token: String,
        @Field("patient_id") patient_id: String
    ): Deferred<GetMedicaHistoryModel>


    companion object {
        fun getService(): InstaDoctorApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
//                .baseUrl("http://18.218.168.60/hotshelf/api/")
                .baseUrl("https://www.hotshelf.com/dev/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(InstaDoctorApiService::class.java)
            // .baseUrl("https://androidwave.com")
        }
    }
}


object InstaDoctorApi {
    val retrofitService: InstaDoctorApiService by lazy { retrofit.create(InstaDoctorApiService::class.java) }
}

