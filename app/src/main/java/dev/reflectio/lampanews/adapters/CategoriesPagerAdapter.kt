package dev.reflectio.lampanews.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.reflectio.lampanews.R
import dev.reflectio.lampanews.ui.FeedFragment


private const val CATEGORIES_AMOUNT = 3


class SectionsPagerAdapter(private val fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FeedFragment.newInstance(fa.getString(R.string.category_1))
            1 -> FeedFragment.newInstance(fa.getString(R.string.category_2))
            else -> FeedFragment.newInstance(fa.getString(R.string.category_3))
        }
    }

    override fun getItemCount() = CATEGORIES_AMOUNT
}
