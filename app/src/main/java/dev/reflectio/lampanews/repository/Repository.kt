package dev.reflectio.lampanews.repository

class Repository (private val newsService: NewsService) {
    fun getAllNews(page: Int = 1) = newsService.getAllNews(page)
}