package ru.vld43.mangadexapp.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.app.App
import ru.vld43.mangadexapp.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private companion object {
        const val SPAN_COUNT = 3
        const val SEARCH_DELAY = 300
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var mangaAdapter: MangaAdapter

    private val disposables = CompositeDisposable()

    private val mangaListObserver = Observer<MangaStateData> {
        when (it) {
            MangaStateData.Loading -> {
                binding.mangaSrl.isRefreshing = true
                binding.queryErrorLayout.root.isVisible = false
            }
            is MangaStateData.Error -> {
                mangaAdapter.mangaList = emptyList()

                binding.mangaSrl.isRefreshing = false

                binding.notFoundLayout.root.isVisible = false
                binding.queryErrorLayout.root.isVisible = true

                showSnackBar(getString(R.string.connection_error))
            }
            is MangaStateData.Success -> {
                binding.queryErrorLayout.root.isVisible = false
                binding.mangaSrl.isRefreshing = false

                if (it.data.isEmpty()) {
                    binding.notFoundLayout.root.isVisible = true
                    mangaAdapter.mangaList = emptyList()
                } else {
                    binding.notFoundLayout.root.isVisible = false
                    mangaAdapter.mangaList = it.data
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        setContentView(binding.root)

        initViews()
        initRecycler()
        initData()
        disposables.addAll(initSearchView())
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun initRecycler() {
        mangaAdapter = MangaAdapter()
        binding.mangaListRv.adapter = mangaAdapter
        binding.mangaListRv.layoutManager = GridLayoutManager(this, SPAN_COUNT)
    }

    private fun initData() {
        if (binding.mangaSv.query.isNotEmpty()) {
            viewModel.searchManga(binding.mangaSv.query.toString())
        } else {
            viewModel.loadManga()
        }
    }

    private fun observeViewModel() {
        viewModel.mangaList.observe(this, mangaListObserver)
    }

    private fun initViews() {
        binding.mangaSrl.setOnRefreshListener {
            initData()
        }
    }

    private fun initSearchView() =
        Observable.create<String> {
            binding.mangaSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String): Boolean {
                    it.onNext(newText)
                    return false
                }
            })
        }
            .debounce(SEARCH_DELAY.toLong(), TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel.searchManga(it)
            }

    private fun showSnackBar(string: String) =
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()
}

