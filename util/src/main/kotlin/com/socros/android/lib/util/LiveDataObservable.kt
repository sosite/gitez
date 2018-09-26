package com.socros.android.lib.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Created by zhaowei on 2017/05/19.
 * Updated by sosite on 2018/09/24.
 */
class LiveDataObservable<T>(private val owner: LifecycleOwner?, private val data: LiveData<T>) : Observable<Emit<T>>() {

	override fun subscribeActual(observer: Observer<in Emit<T>>) {
		val liveDataObserver = LiveDataObserver(data, observer)
		observer.onSubscribe(liveDataObserver)
		if (owner == null) {
			data.observeForever(liveDataObserver)
		} else {
			data.observe(owner, liveDataObserver)
		}
	}

	class LiveDataObserver<T>(private val data: LiveData<T>, private val observer: Observer<in Emit<T>>)
		: MainThreadDisposable(), androidx.lifecycle.Observer<T> {

		override fun onDispose() {
			data.removeObserver(this)
		}

		override fun onChanged(t: T?) {
			observer.onNext(Emit(t))
		}
	}
}

class Emit<T> internal constructor(val data: T?)

fun <T> LiveData<T>.toObservable(owner: LifecycleOwner): Observable<Emit<T>> {
	return LiveDataObservable(owner, this)
}

fun <T> LiveData<T>.toForeverObservable(): Observable<Emit<T>> {
	return LiveDataObservable(null, this)
}
