package ru.vld43.mangadexapp.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.app.App
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val testTv by lazy { findViewById<TextView>(R.id.test_tv) }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        observeManga()
    }

    private fun observeManga() = viewModel.mangaListState.observe(this, { mangaList ->
        var result = ""

        mangaList.map {
            result += "id: ${it.id}\n\n" +
                    "title: ${it.title}\n\n" +
                    "description: ${it.description}\n\n" +
                    "------------------------------------------------------------------------------\n\n"
        }

        testTv.text = result
    })

}