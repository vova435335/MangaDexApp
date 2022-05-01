package ru.vld43.mangadexapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.extensions.observe
import ru.vld43.mangadexapp.databinding.FragmentMainBinding
import ru.vld43.mangadexapp.ui.MainActivity
import ru.vld43.mangadexapp.ui.main.adapters.MangaAdapter
import javax.inject.Inject

private const val SPAN_COUNT = 3

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private lateinit var binding: FragmentMainBinding

    private lateinit var mangaAdapter: MangaAdapter

    private val disposables = CompositeDisposable()

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

        initViews()
        initRecycler()
        initData()
        initSearchView()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun initViews() {
        binding.mangaSrl.setOnRefreshListener {
            mangaAdapter.refresh()
        }
    }

    private fun initRecycler() {
        mangaAdapter = MangaAdapter(viewModel::openDetails)
        binding.mangaListRv.adapter = mangaAdapter
        binding.mangaListRv.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
        mangaAdapter.addLoadStateListener {
            when (it.refresh) {
                LoadState.Loading -> {
                    binding.mangaSrl.isRefreshing = true
                    binding.queryErrorLayout.root.isVisible = false
                }
                is LoadState.Error -> {
                    binding.mangaSrl.isRefreshing = false

                    binding.notFoundLayout.root.isVisible = false
                    binding.queryErrorLayout.root.isVisible = true

                    showSnackBar(getString(R.string.connection_error))
                }
                is LoadState.NotLoading -> {
                    binding.queryErrorLayout.root.isVisible = false
                    binding.mangaSrl.isRefreshing = false

                    binding.notFoundLayout.root.isVisible = false
                }
            }
        }
    }

    private fun initData() {
        if (binding.mangaSv.query.isNotEmpty()) {
            viewModel.searchManga(binding.mangaSv.query.toString())
        } else {
            viewModel.loadManga()
        }
    }

    private fun observeViewModel() {
        viewModel.mangaListState.observe(this) {
            mangaAdapter.submitData(lifecycle, it)
        }
    }

    private fun initSearchView() {
        binding.mangaSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchManga(newText)
                return false
            }
        })
    }

    private fun showSnackBar(string: String) =
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()
}
