package com.socros.android.lib.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.mapToTrue(): Observable<Boolean> = this.map { _ -> true }

@Suppress("UNCHECKED_CAST")
fun <T> Observable<T>.composeAsync(): Observable<T> =
		this.compose(asyncTransformer as ObservableTransformer<in T, out T>)

private val asyncTransformer = AsyncTransformer<Any>()

private class AsyncTransformer<T> : ObservableTransformer<T, T> {
	override fun apply(upstream: Observable<T>): ObservableSource<T> {
		return upstream.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
	}
}

/**
 * Returns an Observable that emits the items emitted by the source ObservableSource shifted forward in time to
 * a specified delay only if it's shorter than specified delay. If `delayError` is true, error notifications
 * will be delayed to at least `delay`.
 *
 * **Scheduler:**
 *
 * &nbsp;        This version of `delay` operates by default on the `computation` [`Scheduler`][io.reactivex.Scheduler]
 *
 * @param delay the delay to shift the source to
 * @param unit the {@link TimeUnit} in which {@code period} is defined
 * @param delayError if `true`, the upstream exception is signalled with at least given delay or after all preceding
 * normal elements, if `false`, the upstream exception is signalled immediately
 * @return the source ObservableSource shifted in time to at least the specified delay
 */
fun <T> Observable<T>.delayToAtLeast(delay: Long, unit: TimeUnit, delayError: Boolean = false): Observable<T> {
	return Observable.zip(
			this,
			Observable.timer(delay, unit),
			BiFunction { source, _ -> source },
			delayError
	)
}
