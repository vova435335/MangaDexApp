package ru.vld43.mangadexapp.ui.details

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
import ru.vld43.mangadexapp.common.extensions.observe
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

        binding.readMangaButton.setOnClickListener {
            viewModel.openChapters(arguments.mangaId)
        }
    }

    private fun initData(mangaId: String) {
        viewModel.loadManga(mangaId)
    }

    private fun observeViewModel() {
        viewModel.mangaState.observe(this) {
            when (it) {
                is LoadState.Loading -> {}
                is LoadState.Success -> initViews(it.data ?: MangaDetailsWithCover())
                is LoadState.Error -> Log.d("TAG", "observeViewModel: ${it.message}")
            }
        }
    }

    private fun initViews(manga: MangaDetailsWithCover) {
        with(binding) {
            if (manga.coverUrl.isNotEmpty()) {
                Picasso.get()
                    .load(manga.coverUrl)
                    .into(detailsCoverArtIv)
            }
            detailsTitleTv.text = manga.title
            detailsDescriptionTv.text = manga.description

            if(detailsTagCg.size == 0) {
                manga.tags.map { tag ->
                    val chip = Chip(context)
                    chip.text = tag
                    detailsTagCg.addView(chip)
                }
            }

            detailsStatusValueTv.text = manga.status
            detailsContentRatingValueTv.text = manga.contentRating

            if(manga.lastChapter != null) {
                detailsLastChapterValueTv.text = manga.lastChapter
            } else {
                diverBottom.isVisible = false
                detailsLastChapterTv.isVisible = false
                detailsLastChapterValueTv.isVisible = false
            }
        }
    }
}
