package ru.vld43.mangadexapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vld43.mangadexapp.common.data.UrlConstants
import ru.vld43.mangadexapp.data.remote.MangaDexApi
import ru.vld43.mangadexapp.data.remote.deserialize.LocalizedStringDeserializer
import ru.vld43.mangadexapp.data.remote.response.manga.LocalizedString
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
        .baseUrl(UrlConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Singleton
    @Provides
    fun provideMangaDexApi(retrofit: Retrofit): MangaDexApi =
        retrofit.create(MangaDexApi::class.java)
}
