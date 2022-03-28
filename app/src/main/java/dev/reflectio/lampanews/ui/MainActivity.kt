package dev.reflectio.lampanews.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.tabs.TabLayoutMediator
import dev.reflectio.lampanews.R
import dev.reflectio.lampanews.SharedViewModel
import dev.reflectio.lampanews.SharedViewModelFactory
import dev.reflectio.lampanews.databinding.ActivityMainBinding
import dev.reflectio.lampanews.repository.NewsService
import dev.reflectio.lampanews.repository.Repository
import dev.reflectio.lampanews.adapters.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val retrofitService = NewsService.getInstance()
    private val viewModel: SharedViewModel by viewModels {
        SharedViewModelFactory(Repository(retrofitService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setContentView(binding.root)

        setupObservers()

        if (viewModel.requestSucceed.value == null)
            viewModel.getAllNews()

        setupViewPager()

        setupToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val icon = menu.getItem(0).icon
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.maxWidth = 10_000

        DrawableCompat.setTint(icon, ContextCompat.getColor(this, android.R.color.white))

        return true
    }

    private fun toggleErrorScreen() {
        binding.apply {
            errorButton.visibility = View.VISIBLE
            errorImage.visibility = View.VISIBLE
            errorText.visibility = View.VISIBLE

            tabs.visibility = View.GONE
        }
    }

    private fun disableErrorScreen() {
        binding.apply {
            errorButton.visibility = View.GONE
            errorImage.visibility = View.GONE
            errorText.visibility = View.GONE

            tabs.visibility = View.VISIBLE
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        viewPager.adapter = SectionsPagerAdapter(this)

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.category_1)
                1 -> tab.text = getString(R.string.category_2)
                else -> tab.text = getString(R.string.category_3)
            }
        }.attach()
    }

    private fun setupObservers() {
        viewModel.errorMessage.observe(this) {
            if (it != null)
                toggleErrorScreen()
        }

        viewModel.requestSucceed.observe(this) {
            disableErrorScreen()
        }
    }
}