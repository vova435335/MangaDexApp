package ru.vld43.mangadexapp.ui.chapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.vld43.mangadexapp.databinding.FragmentChaptersBinding
import ru.vld43.mangadexapp.databinding.FragmentMangaDetailsBinding

class ChaptersFragment: Fragment() {

    private lateinit var binding: FragmentChaptersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChaptersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
