package dev.reflectio.lampanews.repository

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("title") var title: String,
    @SerializedName("type") var type: String?,
    @SerializedName("img") var imageUrl: String?,
    @SerializedName("click_url") var clickUrl: String?,
    @SerializedName("time") var time: String?,
    @SerializedName("top") var top: String?
)