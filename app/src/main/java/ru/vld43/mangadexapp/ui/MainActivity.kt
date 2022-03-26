package ru.vld43.mangadexapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.common.Constants.BASE_URL
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.deserialize.LocalizedStringDeserializer
import ru.vld43.mangadexapp.data.remote.dto.LocalizedString
import ru.vld43.mangadexapp.data.repository.MangaRepositoryImpl
import ru.vld43.mangadexapp.domain.use_case.GetMangaListUseCase

class MainActivity : AppCompatActivity() {

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .registerTypeAdapter(LocalizedString::class.java, LocalizedStringDeserializer())
            .create() }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    private val mangaDexApi by lazy { retrofit.create(MangaDexApi::class.java) }
    private val mangaRepository by lazy { MangaRepositoryImpl(mangaDexApi) }
    private val getMangaListUseCase by lazy { GetMangaListUseCase(mangaRepository) }

    private val testTv by lazy { findViewById<TextView>(R.id.test_tv) }

    private val disposables = CompositeDisposable()
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposables.add(loadManga())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
    private fun loadManga() = getMangaListUseCase.execute()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ manga ->
            Log.d("MainActivity", "qqq $manga: ")
            manga.map {
                result += "${it.id}\n\n${it.title}\n\n${it.description}\n" +
                        "------------------------------------------------------------------------\n"
            }
            testTv.text = result
        }, {
            Log.d("MainActivity", "qqq ${it.message}: ")
            testTv.text = it.message
        })

}