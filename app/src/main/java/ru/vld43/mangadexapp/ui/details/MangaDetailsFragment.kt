package ru.vld43.mangadexapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import ru.vld43.mangadexapp.common.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentMangaDetailsBinding
import ru.vld43.mangadexapp.domain.models.MangaDetailsWithCover
import ru.vld43.mangadexapp.ui.MainActivity
import ru.vld43.mangadexapp.ui.states.LoadMangaState
import javax.inject.Inject

class MangaDetailsFragment : Fragment() {

    private val arguments: MangaDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: MangaDetailsViewModelFactory
    private val viewModel by viewModels<MangaDetailsViewModel> { viewModelFactory }

    private lateinit var binding: FragmentMangaDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMangaDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)

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
                LoadMangaState.Loading -> {}
                is LoadMangaState.Success<*> -> initViews(it.data as MangaDetailsWithCover)
                is LoadMangaState.Error -> Log.d("TAG", "observeViewModel: ${it.message}")
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
            manga.tags.map { tag ->
                val chip = Chip(context)
                chip.text = tag
                detailsTagCg.addView(chip)
            }
            detailsStatusValueTv.text = manga.status
            detailsContentRatingValueTv.text = manga.contentRating
            detailsLastChapterValueTv.text = manga.lastChapter
        }
    }
}
