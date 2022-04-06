package ru.vld43.mangadexapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.app.App
import ru.vld43.mangadexapp.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private companion object {
        const val SPAN_COUNT = 3
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var mangaAdapter: MangaAdapter

    private val mangaListObserver = Observer<MangaStateData> {
        when (it) {
            MangaStateData.Loading -> {
                binding.mangaSrl.isRefreshing = true
                binding.notFountLayout.root.isVisible = false
            }
            is MangaStateData.Error -> {
                mangaAdapter.mangaList = emptyList()
                binding.mangaSrl.isRefreshing = false
                binding.notFountLayout.root.isVisible = true

                binding.notFountLayout.queryErrorTv.text = getString(R.string.query_error)
                showSnackBar(getString(R.string.connection_error))
            }
            is MangaStateData.Success -> {
                binding.notFountLayout.root.isVisible = false
                binding.mangaSrl.isRefreshing = false

                mangaAdapter.mangaList = it.data
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
        observeViewModel()
    }

    private fun initRecycler() {
        mangaAdapter = MangaAdapter()
        binding.mangaListRv.adapter = mangaAdapter
        binding.mangaListRv.layoutManager = GridLayoutManager(this, SPAN_COUNT)
    }

    private fun initData() {
        viewModel.loadManga()
    }

    private fun observeViewModel() {
        viewModel.mangaList.observe(this, mangaListObserver)
    }

    private fun initViews() {
        binding.mangaSrl.setOnRefreshListener {
            initData()
        }
    }

    private fun showSnackBar(string: String) =
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()

}