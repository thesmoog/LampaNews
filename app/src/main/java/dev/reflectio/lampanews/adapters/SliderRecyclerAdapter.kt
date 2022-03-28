package dev.reflectio.lampanews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import dev.reflectio.lampanews.databinding.SliderItemBinding
import dev.reflectio.lampanews.repository.NewsItem


class SliderRecyclerAdapter(private val sliderAdapter: SliderPagerAdapter) :
    RecyclerView.Adapter<SliderRecyclerAdapter.SliderViewHolder>() {

    private var itemsCount = 0

    inner class SliderViewHolder(val binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setSliderNewsList(news: List<NewsItem>) {
        sliderAdapter.setNewsList(news)

        if (news.isNotEmpty() && itemsCount != 1) {
            itemsCount = 1
            notifyItemInserted(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SliderViewHolder(SliderItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.binding.viewPager.apply {
            adapter = sliderAdapter
            TabLayoutMediator(holder.binding.tabLayout, this) { _, _ -> }.attach()
        }
    }

    override fun getItemCount() = itemsCount
}