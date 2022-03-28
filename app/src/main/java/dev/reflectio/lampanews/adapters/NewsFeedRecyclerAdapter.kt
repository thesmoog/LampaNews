package dev.reflectio.lampanews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.reflectio.lampanews.databinding.NewsItemBinding
import dev.reflectio.lampanews.repository.NewsItem


class NewsFeedRecyclerAdapter :
    RecyclerView.Adapter<NewsFeedRecyclerAdapter.NewsViewHolder>() {

    private var news = mutableListOf<NewsItem>()

    inner class NewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setNewsList(news: List<NewsItem>) {
        if (this.news != news) {
            this.news = news.toMutableList()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(NewsItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = news[position]
        holder.binding.item = newsItem

        newsItem.imageUrl?.let {
            holder.binding.imageCover.apply {
                this.visibility = View.VISIBLE
                Glide.with(holder.itemView.context).load(it).into(this)
            }
        }
    }

    override fun getItemCount() = news.size
}