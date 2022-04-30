package ru.vld43.mangadexapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.databinding.FragmentMainBinding
import ru.vld43.mangadexapp.ui.MainActivity
import ru.vld43.mangadexapp.ui.navigation.AppNavigator
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.appComponent.inject(this)


        binding.btn.setOnClickListener {
            viewModel.openDetails()
        }
    }

}