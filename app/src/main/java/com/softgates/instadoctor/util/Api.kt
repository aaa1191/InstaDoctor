package com.softgates.instadoctor.util

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.softgates.instadoctor.network.ResponseModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import retrofit2.http.GET
import java.util.HashMap
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

    // @FieldMap
  //  @Multipart

 //   @FormUrlEncoded

    @POST("login")
    fun login(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("email") catId: String,
        @Field("password") min_price: String,
        @Field("device_type") device_type: String,
        @Field("device_id") device_id: String
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("user/verify")
    fun verify(
        @Field("code") catId: String
    ): Deferred<ResponseModel>

    @POST("signup")
    fun signup(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    @POST("contact")
    fun contact(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    @POST("subcat")
    fun getSubCategory(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>

    @POST("viewproperty")
    fun getProperty(
        @Body hashMap: Map<String, String>
    ): Deferred<ResponseModel>


    @FormUrlEncoded
    @POST("index.php")
    fun login(
        @Field("apiname") apiname: String,
        @Field("patient_email") email: String,
        @Field("patient_password") password: String,
        @Field("token") device_type: String,
      //  @Field("device_id") device_id: String,
        @Field("role_id") type: String
    ): Deferred<ResponseModel>

    @FormUrlEncoded
    @POST("index.php")
    fun forgetpassword(
        @Field("apiname") apiname: String,
        @Field("email") email: String
    ): Deferred<ResponseModel>

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

