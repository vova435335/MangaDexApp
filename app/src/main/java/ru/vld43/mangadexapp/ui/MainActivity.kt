package ru.vld43.mangadexapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.app.App
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private companion object {
        const val SPAN_COUNT = 3
    }

    private val mangaListRecycler by lazy { findViewById<RecyclerView>(R.id.manga_list_rv) }
    private val mangaSwipeRefresh by lazy { findViewById<SwipeRefreshLayout>(R.id.manga_srl) }

    private lateinit var mangaAdapter: MangaAdapter

    private val mangaListObserver = Observer<MangaStateData> {
        when (it) {
            MangaStateData.Loading -> {
                mangaSwipeRefresh.isRefreshing = true
            }
            is MangaStateData.Error -> {
                mangaSwipeRefresh.isRefreshing = false
            }
            is MangaStateData.Success -> {
                mangaSwipeRefresh.isRefreshing = false
                mangaAdapter.mangaList = it.data
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        initViews()
        initRecycler()
        initData()
        observeViewModel()
    }

    private fun initRecycler() {
        mangaAdapter = MangaAdapter()
        mangaListRecycler.adapter = mangaAdapter
        mangaListRecycler.layoutManager = GridLayoutManager(this, SPAN_COUNT)
    }

    private fun initData() {
        viewModel.loadManga()
    }

    private fun observeViewModel() {
        viewModel.mangaList.observe(this, mangaListObserver)
    }

    private fun initViews() {
        mangaSwipeRefresh.setOnRefreshListener {
            initData()
        }
    }

}