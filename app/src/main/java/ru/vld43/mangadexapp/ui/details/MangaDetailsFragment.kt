package ru.vld43.mangadexapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import ru.vld43.mangadexapp.common.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentMangaDetailsBinding
import ru.vld43.mangadexapp.ui.MainActivity
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
    }

    private fun initData(mangaId: String) {
        viewModel.loadManga(mangaId)
    }

    private fun observeViewModel() {
        viewModel.mangaState.observe(this) {
            Log.d("TAG", "observeViewModel: $it")
            with(binding) {
                if(it.coverUrl.isNotEmpty()) {
                    Picasso.get()
                        .load(it.coverUrl)
                        .into(detailsCoverArtIv)
                }
                detailsTitleTv.text = it.title
                detailsDescriptionTv.text = it.description
                detailsContentRatingValueTv.text = it.contentRating
                detailsLastChapterValueTv.text = it.lastChapter
            }
        }
    }

}