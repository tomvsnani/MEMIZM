package com.analogit.memeizm

import com.analogit.memeizm.Models.MainContentResponseModel
import com.analogit.memeizm.Models.MainResponseModelClass
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitInterface {
    @FormUrlEncoded
    @POST("getcollection")
    fun getMainPageTemplates(@Field("collection_id") collectionId: String): Call<MainResponseModelClass>

    @FormUrlEncoded
    @POST("getcollection")
    fun getSearchPageTemplates(@Field("collection_id") id: String): Call<MainResponseModelClass>


    @GET("searchcollections")
    fun getSearchCollections(): Call<MainContentResponseModel>

    @GET("collections")
    fun getHomePageCollections(): Call<MainContentResponseModel>


}