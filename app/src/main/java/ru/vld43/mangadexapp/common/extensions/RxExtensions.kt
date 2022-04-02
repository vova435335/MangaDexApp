package ru.vld43.mangadexapp.common.extensions

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers() =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())