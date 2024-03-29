package ru.vld43.mangadexapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vld43.mangadexapp.BuildConfig
import ru.vld43.mangadexapp.data.network.MangaDexApi
import ru.vld43.mangadexapp.data.network.deserialize.LocalizedStringDeserializer
import ru.vld43.mangadexapp.data.network.response.manga.LocalizedString
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .registerTypeAdapter(LocalizedString::class.java, LocalizedStringDeserializer())
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideMangaDexApi(retrofit: Retrofit): MangaDexApi =
        retrofit.create(MangaDexApi::class.java)
}
