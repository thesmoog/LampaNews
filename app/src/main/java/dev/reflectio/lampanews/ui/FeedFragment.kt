package dev.reflectio.lampanews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import dev.reflectio.lampanews.SharedViewModel
import dev.reflectio.lampanews.adapters.NewsFeedRecyclerAdapter
import dev.reflectio.lampanews.adapters.SliderPagerAdapter
import dev.reflectio.lampanews.adapters.SliderRecyclerAdapter
import dev.reflectio.lampanews.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    private var category: String? = null
    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerAdapter = NewsFeedRecyclerAdapter()
        val sliderPagerAdapter = SliderPagerAdapter(requireActivity(), listOf())
        val sliderAdapter = SliderRecyclerAdapter(sliderPagerAdapter)

        val concatAdapter = ConcatAdapter(sliderAdapter, recyclerAdapter)
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = concatAdapter
        }

        // Handle News Feed source updates
        val newsList = viewModel.getNewsByCategory(requireContext(), category!!)
        newsList.observe(requireActivity()) {
            recyclerAdapter.setNewsList(it)
        }

        // Handle TOP News source updates for slider
        val topNewsList = viewModel.getTopNewsByCategory(requireContext(), category!!)
        topNewsList.observe(requireActivity()) {
            sliderAdapter.setSliderNewsList(it)
        }
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        @JvmStatic
        fun newInstance(category: String) =
            FeedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}