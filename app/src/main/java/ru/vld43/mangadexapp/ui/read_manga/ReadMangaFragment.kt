package ru.vld43.mangadexapp.ui.read_manga

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentReadMangaBinding
import ru.vld43.mangadexapp.ui.MainActivity
import ru.vld43.mangadexapp.ui.states.LoadState
import javax.inject.Inject

class ReadMangaFragment : Fragment(R.layout.fragment_read_manga) {

    private val arguments: ReadMangaFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ReadMangaViewModelFactory
    private val viewModel by viewModels<ReadMangaViewModel> { viewModelFactory }

    private lateinit var binding: FragmentReadMangaBinding

    private lateinit var readMangaAdapter: ReadMangaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)

        binding = FragmentReadMangaBinding.bind(view)

        initRecycler()
        initData(arguments.chapterId)
        observeViewModel()
    }

    private fun initRecycler() {
        readMangaAdapter = ReadMangaAdapter()
        binding.readMangaRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = readMangaAdapter
        }
    }

    private fun initData(chapterId: String) {
        viewModel.loadChapterPages(chapterId)
    }

    private fun observeViewModel() {
        viewModel.chapterPagesState.observe(this) {
            when (it) {
                is LoadState.Loading -> {}
                is LoadState.Success -> {
                    readMangaAdapter.chapterPages = it.data ?: emptyList()
                }
                is LoadState.Error -> Log.d("TAG", "observeViewModel: ${it.message}")
            }
        }
    }
}
