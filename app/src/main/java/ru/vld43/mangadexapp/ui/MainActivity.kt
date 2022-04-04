package ru.vld43.mangadexapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.app.App
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val mangaListRecycler by lazy { findViewById<RecyclerView>(R.id.manga_list_rv) }

    private lateinit var mangaAdapter: MangaAdapter

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        initRecycler()
        observeManga()
    }

    private fun initRecycler() {
        mangaAdapter = MangaAdapter()
        mangaListRecycler.adapter = mangaAdapter
        mangaListRecycler.layoutManager = GridLayoutManager(this, 3)
    }

    private fun observeManga() = viewModel.mangaListState.observe(this, {
        mangaAdapter.mangaList = it
    })

}