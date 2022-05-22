package ru.vld43.mangadexapp.ui.chapters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.databinding.FragmentChaptersBinding
import ru.vld43.mangadexapp.ui.MainActivity
import javax.inject.Inject

class ChaptersFragment: Fragment(R.layout.fragment_chapters) {

    private val arguments: ChaptersFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ChaptersViewModelFactory
    private val viewModel by viewModels<ChaptersViewModel> { viewModelFactory }

    private lateinit var binding: FragmentChaptersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)

        binding = FragmentChaptersBinding.bind(view)
    }
}
