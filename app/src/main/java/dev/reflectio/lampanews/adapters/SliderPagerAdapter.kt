package dev.reflectio.lampanews.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.reflectio.lampanews.repository.NewsItem
import dev.reflectio.lampanews.ui.SliderFragment


class SliderPagerAdapter(fa: FragmentActivity, private var fragments: List<SliderFragment>) :
    FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment =
        fragments[position]

    override fun getItemCount() = fragments.size

    fun setNewsList(news: List<NewsItem>) {
        val newFragmentsList = mutableListOf<SliderFragment>()
        news.forEach {
            newFragmentsList.add(
                SliderFragment.newInstance(
                    it.title, it.type, it.imageUrl, it.clickUrl, it.time, it.top
                )
            )
        }

        if (fragments != newFragmentsList) {
            fragments = newFragmentsList
            notifyDataSetChanged()
        }
    }
}