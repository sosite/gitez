package com.socros.android.lib.tim

import timber.log.Timber
import kotlin.properties.Delegates

object Tim {

	private const val PRODUCTION_MSG = "In production environment message parameter still will be executed!"

	var debug: Boolean by Delegates.notNull()
		private set

	fun init(debug: Boolean, productionTree: Timber.Tree? = null) {
		this.debug = debug

		if (debug || productionTree != null) {
			Timber.plant(
					if (debug) Timber.DebugTree()
					else productionTree!!
			)
		}
	}

	@Deprecated(PRODUCTION_MSG, ReplaceWith("Tim.v { message }"))
	fun v(message: String) {
		Timber.v(message)
	}

	inline fun v(lazyMessage: () -> String) {
		if (debug) {
			Timber.v(lazyMessage.invoke())
		}
	}

	@Deprecated(PRODUCTION_MSG, ReplaceWith("Tim.v(throwable) { message }"))
	fun v(message: String, throwable: Throwable?) {
		Timber.v(throwable, message)
	}

	inline fun v(throwable: Throwable?, lazyMessage: () -> String) {
		if (debug) {
			Timber.v(throwable, lazyMessage.invoke())
		}
	}

	@Deprecated(PRODUCTION_MSG, ReplaceWith("Tim.d { message }"))
	fun d(message: String) {
		Timber.d(message)
	}

	inline fun d(lazyMessage: () -> String) {
		if (debug) {
			Timber.d(lazyMessage.invoke())
		}
	}

	@Deprecated(PRODUCTION_MSG, ReplaceWith("Tim.d(throwable) { message }"))
	fun d(message: String, throwable: Throwable?) {
		Timber.d(throwable, message)
	}

	inline fun d(throwable: Throwable?, lazyMessage: () -> String) {
		if (debug) {
			Timber.d(throwable, lazyMessage.invoke())
		}
	}

	fun i(message: String) {
		Timber.i(message)
	}

	fun i(message: String, throwable: Throwable?) {
		Timber.i(throwable, message)
	}

	@Deprecated(PRODUCTION_MSG, ReplaceWith("Tim.w { message }"))
	fun w(message: String) {
		Timber.w(message)
	}

	inline fun w(lazyMessage: () -> String) {
		if (debug) {
			Timber.w(lazyMessage.invoke())
		}
	}

	@Deprecated(PRODUCTION_MSG, ReplaceWith("Tim.w(throwable) { message }"))
	fun w(message: String, throwable: Throwable?) {
		Timber.w(throwable, message)
	}

	inline fun w(throwable: Throwable?, lazyMessage: () -> String) {
		if (debug) {
			w(lazyMessage.invoke(), throwable)
		}
	}

	fun e(message: String) {
		Timber.e(message)
	}

	fun e(message: String, throwable: Throwable?) {
		Timber.e(throwable, message)
	}

	fun wtf(message: String) {
		Timber.wtf(message)
	}

	fun wtf(message: String, throwable: Throwable?) {
		Timber.wtf(throwable, message)
	}

}
