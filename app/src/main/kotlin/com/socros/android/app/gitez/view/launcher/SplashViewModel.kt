package com.socros.android.app.gitez.view.launcher

import com.socros.android.lib.androidcore.view.launcher.ACSplashViewModel
import com.socros.android.lib.util.random
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ACSplashViewModel<Any>() {
	override val splashMinVisibilityTime = 3_000

	private val quoteSubject = BehaviorSubject.create<Quote>()

	fun getQuote(sentencesArray: Array<String>, authorsArray: Array<String>): Observable<Quote> {
		if (!quoteSubject.hasValue()) {
			val randomIndex = (0..sentencesArray.size).random()
			val sentence = sentencesArray[randomIndex]
			val author = authorsArray[randomIndex]
			val quote = Quote(sentence, author)
			quoteSubject.onNext(quote)
		}

		return quoteSubject
	}
}
