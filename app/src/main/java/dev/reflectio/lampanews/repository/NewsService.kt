package dev.reflectio.lampanews.repository

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("/")
    fun getAllNews(@Query("page") page: Int): Call<List<NewsItem>>

    companion object {
        var newsService: NewsService? = null

        fun getInstance(): NewsService {
            if (newsService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://188.40.167.45:3001")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                newsService = retrofit.create(NewsService::class.java)
            }
            return newsService!!
        }
    }
}