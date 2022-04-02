package ru.vld43.mangadexapp.di

import dagger.Module

@Module(includes = [NetworkModule::class, DataBindModule::class])
class DataModule