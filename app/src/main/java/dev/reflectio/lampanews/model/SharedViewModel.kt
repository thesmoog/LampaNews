package dev.reflectio.lampanews

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.reflectio.lampanews.repository.NewsItem
import dev.reflectio.lampanews.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel(private val repository: Repository) : ViewModel() {

    private val _storiesList = MutableLiveData<List<NewsItem>>(listOf())
    private val _storiesTopList = MutableLiveData<List<NewsItem>>(listOf())

    private val _videosList = MutableLiveData<List<NewsItem>>(listOf())
    private val _videosTopList = MutableLiveData<List<NewsItem>>(listOf())

    private val _favouritesList = MutableLiveData<List<NewsItem>>(listOf())
    private val _favouritesTopList = MutableLiveData<List<NewsItem>>(listOf())

    val errorMessage = MutableLiveData<String?>(null)

    val requestSucceed = MutableLiveData<Boolean>()

    fun getAllNews(page: Int = 1) {
        val response = repository.getAllNews(page)
        response.enqueue(object : Callback<List<NewsItem>> {
            override fun onResponse(
                call: Call<List<NewsItem>>,
                response: Response<List<NewsItem>>
            ) {
                requestSucceed.postValue(true)
                val news = response.body()

                if (news.isNotEmpty()) {
                    val stories = news.filter { it.type == "strories" }
                    val video = news.filter { it.type == "video" }
                    val favourites = news.filter { it.type == "favourites" }

                    val topStories = stories.filter { it.top == "1" }
                    val topVideo = video.filter { it.top == "1" }
                    val topFavourites = favourites.filter { it.top == "1" }

                    _storiesTopList.postValue(_storiesTopList.value!!.plus(topStories))
                    _videosTopList.postValue(_videosTopList.value!!.plus(topVideo))
                    _favouritesTopList.postValue(_favouritesTopList.value!!.plus(topFavourites))

                    _storiesList.postValue(_storiesList.value!!.plus(stories))
                    _videosList.postValue(_videosList.value!!.plus(video))
                    _favouritesList.postValue(_favouritesList.value!!.plus(favourites))

                    getAllNews(page + 1)
                }
            }

            override fun onFailure(call: Call<List<NewsItem>>, t: Throwable) =
                errorMessage.postValue(t.message)
        })

    }

    fun getNewsByCategory(context: Context, category: String): LiveData<List<NewsItem>> {
        return when (category) {
            context.getString(R.string.category_1) -> _storiesList
            context.getString(R.string.category_2) -> _videosList
            context.getString(R.string.category_3) -> _favouritesList
            else -> throw IllegalArgumentException("Unexpected category.")
        }
    }

    fun getTopNewsByCategory(context: Context, category: String): LiveData<List<NewsItem>> {
        return when (category) {
            context.getString(R.string.category_1) -> _storiesTopList
            context.getString(R.string.category_2) -> _videosTopList
            context.getString(R.string.category_3) -> _favouritesTopList
            else -> throw IllegalArgumentException("Unexpected category.")
        }
    }
}

class SharedViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            SharedViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}