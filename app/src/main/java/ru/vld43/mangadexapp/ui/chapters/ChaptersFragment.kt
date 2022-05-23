package ru.vld43.mangadexapp.ui.chapters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentChaptersBinding
import ru.vld43.mangadexapp.ui.MainActivity
import ru.vld43.mangadexapp.ui.chapters.adapters.ChaptersAdapter
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

        initViews()
        initRecycler()
        initData(arguments.mangaId)
        observeViewModel()
    }

    private fun initViews() {
        binding.chapterSrl.setOnRefreshListener {
            chaptersAdapter.refresh()
        }
    }

    private fun initRecycler() {
        chaptersAdapter = ChaptersAdapter()
        binding.chaptersRv.adapter = chaptersAdapter
        binding.chaptersRv.layoutManager = LinearLayoutManager(requireContext())
        chaptersAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.chapterSrl.isRefreshing = true
                }
                is LoadState.Error -> {
                    binding.chapterSrl.isRefreshing = false
                    Log.d("TAG", "initRecycler: ERROR")
                }
                is LoadState.NotLoading -> {
                    binding.chapterSrl.isRefreshing = false
                }
            }
        }
    }

    private fun initData(mangaId: String) {
        viewModel.loadChapters(mangaId)
    }

    private fun observeViewModel() {
        viewModel.chaptersState.observe(this) {
            chaptersAdapter.submitData(lifecycle, it)
        }
    }
}
