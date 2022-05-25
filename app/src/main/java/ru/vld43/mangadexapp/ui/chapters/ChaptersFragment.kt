package ru.vld43.mangadexapp.ui.chapters

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.data.ui.DefaultLoadStateAdapter
import ru.vld43.mangadexapp.common.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentChaptersBinding
import ru.vld43.mangadexapp.ui.MainActivity
import javax.inject.Inject

class ChaptersFragment : Fragment(R.layout.fragment_chapters) {

    private val arguments: ChaptersFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ChaptersViewModelFactory
    private val viewModel by viewModels<ChaptersViewModel> { viewModelFactory }

    private lateinit var binding: FragmentChaptersBinding

    private lateinit var chaptersAdapter: ChaptersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)

        binding = FragmentChaptersBinding.bind(view)

        initRefresh()
        initRecycler()
        initData(arguments.mangaId)
        observeViewModel()
    }

    private fun initRefresh() {
        binding.chapterSrl.setOnRefreshListener {
            chaptersAdapter.refresh()
        }
    }

    private fun initRecycler() {
        chaptersAdapter = ChaptersAdapter(viewModel::openReadManga)
        binding.chaptersRv.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = chaptersAdapter.withLoadStateFooter(
                footer = DefaultLoadStateAdapter { chaptersAdapter.retry() }
            )
        }

        listenLoadState()
    }

    private fun initData(mangaId: String) {
        viewModel.loadChapters(mangaId)
    }

    private fun observeViewModel() {
        viewModel.chaptersState.observe(this) {
            chaptersAdapter.submitData(lifecycle, it)
        }
    }

    private fun listenLoadState() {
        chaptersAdapter.addLoadStateListener {
            with(binding) {
                when (it.refresh) {
                    is LoadState.Loading -> {
                        chapterSrl.isRefreshing = true
                    }
                    is LoadState.Error -> {
                        chaptersRv.isVisible = false
                        chapterSrl.isRefreshing = false
                        chaptersNotFound.root.isVisible = false

                        chaptersQueryError.root.isVisible = true
                    }
                    is LoadState.NotLoading -> {
                        chapterSrl.isRefreshing = false
                        chaptersQueryError.root.isVisible = false
                        chaptersNotFound.root.isVisible = chaptersAdapter.itemCount == 0

                        chaptersRv.isVisible = true
                    }
                }
            }
        }
    }
}
