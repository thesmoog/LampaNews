package dev.reflectio.lampanews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dev.reflectio.lampanews.databinding.FragmentSlideBinding
import dev.reflectio.lampanews.repository.NewsItem

private const val ARG_TITLE = "title"
private const val ARG_TYPE = "type"
private const val ARG_IMAGE_URL = "imageUrl"
private const val ARG_CLICK_URL = "clickUrl"
private const val ARG_TIME = "time"
private const val ARG_TOP = "top"

class SliderFragment : Fragment() {
    private lateinit var newsItem: NewsItem
    private lateinit var binding: FragmentSlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsItem = NewsItem(
                it.getString(ARG_TITLE)!!,
                it.getString(ARG_TYPE),
                it.getString(ARG_IMAGE_URL),
                it.getString(ARG_CLICK_URL),
                it.getString(ARG_TIME),
                it.getString(ARG_TOP)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSlideBinding.inflate(inflater, container, false)
        binding.item = newsItem

        newsItem.imageUrl?.let {
            Glide.with(requireContext()).load(it).into(binding.imageCover)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(
            title: String,
            type: String?,
            imageUrl: String?,
            clickUrl: String?,
            time: String?,
            top: String?
        ) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_TYPE, type)
                    putString(ARG_IMAGE_URL, imageUrl)
                    putString(ARG_CLICK_URL, clickUrl)
                    putString(ARG_TIME, time)
                    putString(ARG_TOP, top)
                }
            }
    }
}