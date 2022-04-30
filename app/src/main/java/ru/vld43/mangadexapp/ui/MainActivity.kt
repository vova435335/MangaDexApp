package ru.vld43.mangadexapp.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.databinding.ActivityMainBinding
import ru.vld43.mangadexapp.di.AppComponent
import ru.vld43.mangadexapp.di.AppModule
import ru.vld43.mangadexapp.di.DaggerAppComponent
import ru.vld43.mangadexapp.di.NavigationModule
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SPAN_COUNT = 3
private const val SEARCH_DELAY = 300

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var appComponent: AppComponent
    }

//    @Inject
//    lateinit var viewModelFactory2: MainViewModelFactory2
//    private lateinit var viewModel2: MainViewModel2

    private lateinit var binding: ActivityMainBinding

    private lateinit var mangaAdapter: MangaAdapter

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = this))
            .navigationModule(NavigationModule(navController = navController))
            .build()

//        viewModel2 = ViewModelProvider(this, viewModelFactory2)[MainViewModel2::class.java]
        setContentView(binding.root)

        initViews()
        initRecycler()
//        initData()
//        disposables.addAll(initSearchView())
//        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun initRecycler() {
        mangaAdapter = MangaAdapter()
        binding.mangaListRv.adapter = mangaAdapter
        binding.mangaListRv.layoutManager = GridLayoutManager(this, SPAN_COUNT)
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
                    binding.mangaListRv.scrollToPosition(0)
                }
            }
        }
    }

//    private fun initData() {
//        if (binding.mangaSv.query.isNotEmpty()) {
//            viewModel2.searchManga(binding.mangaSv.query.toString())
//        } else {
//            viewModel2.loadManga()
//        }
//    }
//
//    private fun observeViewModel() {
//        viewModel2.mangaList.observe(this) {
//            mangaAdapter.submitData(lifecycle, it)
//        }
//    }

    private fun initViews() {
        binding.mangaSrl.setOnRefreshListener {
            mangaAdapter.refresh()
        }

    }

//    private fun initSearchView() =
//        Observable.create<String> {
//            binding.mangaSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean = false
//
//                override fun onQueryTextChange(newText: String): Boolean {
//                    it.onNext(newText)
//                    return false
//                }
//            })
//        }
//            .debounce(SEARCH_DELAY.toLong(), TimeUnit.MILLISECONDS)
//            .subscribe {
//                viewModel2.searchManga(it)
//            }

    private fun showSnackBar(string: String) =
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()
}

