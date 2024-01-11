package ru.vld43.mangadexapp.ui.manga_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.ui.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentMangaDetailsBinding
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.ui.MainActivity
import ru.vld43.mangadexapp.ui.states.LoadState
import javax.inject.Inject

class MangaDetailsFragment : Fragment(R.layout.fragment_manga_details) {

    private val arguments: MangaDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MangaDetailsViewModelFactory
    private val viewModel by viewModels<MangaDetailsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentMangaDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)

        binding = FragmentMangaDetailsBinding.bind(view)

        observeViewModel()
        initData(arguments.mangaId)
        initRefresh(arguments.mangaId)

        binding.mangaDetailsReadMangaButton.setOnClickListener {
            viewModel.openChapters(arguments.mangaId)
        }
    }

    private fun initRefresh(mangaId: String) {
        binding.mangaDetailsSrl.setOnRefreshListener {
            initData(mangaId)
        }
    }

    private fun initData(mangaId: String) {
        viewModel.loadManga(mangaId)
    }

    private fun observeViewModel() {
        viewModel.mangaState.observe(this) {
            with(binding) {
                when (it) {
                    is LoadState.Loading -> {
                        mangaDetailsSrl.isRefreshing = true
                    }
                    is LoadState.Success -> {
                        initViews(it.data ?: MangaDetailsWithCover())

                        mangaDetailsSrl.isRefreshing = false
                        mangaDetailsQueryError.root.isVisible = false
                        mangaDetailsNotFound.root.isVisible = it.data == null

                        mangaDetailsContentCl.isVisible = true
                    }
                    is LoadState.Error -> {
                        mangaDetailsContentCl.isVisible = false
                        mangaDetailsSrl.isRefreshing = false
                        mangaDetailsNotFound.root.isVisible = false

                        mangaDetailsQueryError.root.isVisible = true
                    }
                }
            }
        }
    }

    private fun initViews(manga: MangaDetailsWithCover) {
        with(binding) {
            if (manga.coverUrl.isNotEmpty()) {
                Picasso.get()
                    .load(manga.coverUrl)
                    .into(mangaDetailsCoverArtIv)
            }
            mangaDetailsTitleTv.text = manga.title
            mangaDetailsDescriptionTv.text = manga.description

            if(mangaDetailsTagCg.size == 0) {
                manga.tags.map { tag ->
                    val chip = Chip(context)
                    chip.text = tag
                    mangaDetailsTagCg.addView(chip)
                }
            }

            mangaDetailsStatusValueTv.text = manga.status
            mangaDetailsContentRatingValueTv.text = manga.contentRating

            if(manga.lastChapter != null) {
                mangaDetailsLastChapterValueTv.text = manga.lastChapter
            } else {
                mangaDetailsDiverBottom.isVisible = false
                mangaDetailsLastChapterTv.isVisible = false
                mangaDetailsLastChapterValueTv.isVisible = false
            }
        }
    }
}
