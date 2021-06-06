package com.tin.ui.main.`interface`

import com.tin.ui.main.Post_class
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface Post_request {
    @GET
    suspend fun getcompany(@Url url: String?): Response<Post_class>
}