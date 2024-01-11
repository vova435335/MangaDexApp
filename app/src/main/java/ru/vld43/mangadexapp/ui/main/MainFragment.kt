package ru.vld43.mangadexapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.ui.DefaultLoadStateAdapter
import ru.vld43.mangadexapp.common.ui.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentMainBinding
import ru.vld43.mangadexapp.ui.MainActivity
import javax.inject.Inject

private const val SPAN_COUNT = 3

class MainFragment : Fragment(R.layout.fragment_main) {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var binding: FragmentMainBinding

    private lateinit var mangaListAdapter: MangaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)

        binding = FragmentMainBinding.bind(view)

        initRefresh()
        initRecycler()
        initData()
        initSearchView()
        observeViewModel()
    }

    private fun initRefresh() {
        binding.mangaListSrl.setOnRefreshListener {
            mangaListAdapter.refresh()
        }
    }

    private fun initRecycler() {
        mangaListAdapter = MangaAdapter(viewModel::openDetails)

        val footerAdapter = DefaultLoadStateAdapter { mangaListAdapter.retry() }
        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)

        binding.mangaListRv.apply {
            this.layoutManager = layoutManager

            adapter = mangaListAdapter.withLoadStateFooter(
                footer = footerAdapter
            )

            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == mangaListAdapter.itemCount && footerAdapter.itemCount > 0) {
                        SPAN_COUNT
                    } else {
                        1
                    }
                }
            }
        }

        listenLoadState()
    }

    private fun initData() {
        if (binding.mangaListSv.query.isNotEmpty()) {
            viewModel.searchManga(binding.mangaListSv.query.toString())
        } else {
            viewModel.loadManga()
        }
    }

    private fun observeViewModel() {
        viewModel.mangaListState.observe(this) {
            mangaListAdapter.submitData(lifecycle, it)
        }
    }

    private fun initSearchView() {
        binding.mangaListSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchManga(newText)
                return false
            }
        })
    }

    private fun listenLoadState() {
        mangaListAdapter.addLoadStateListener {
            with(binding) {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        mangaListSrl.isRefreshing = true
                    }
                    is LoadState.Error -> {
                        mangaListRv.isVisible = false
                        mangaListSrl.isRefreshing = false
                        mangaListNotFound.root.isVisible = false

                        mangaListQueryError.root.isVisible = true
                    }
                    is LoadState.NotLoading -> {
                        mangaListSrl.isRefreshing = false
                        mangaListQueryError.root.isVisible = false
                        mangaListNotFound.root.isVisible = mangaListAdapter.itemCount == 0

                        mangaListRv.isVisible = true
                    }
                }
            }
        }
    }
}
