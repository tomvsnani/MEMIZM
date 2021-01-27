package com.analogit.memeizm

import com.analogit.memeizm.Models.MainResponseModelClass
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitInterface {
@GET("images")
fun getMainPageTemplates(): Call<MainResponseModelClass>

@GET("today")
fun getSearchPageTemplates():Call<MainResponseModelClass>
}