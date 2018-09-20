package com.socros.android.app.gitez.view.launcher

import android.content.Intent
import android.os.Bundle
import androidx.core.view.doOnPreDraw
import com.socros.android.app.gitez.R
import com.socros.android.lib.androidcore.view.launcher.ACSplashActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.splash_activity.authorTxt
import kotlinx.android.synthetic.main.splash_activity.sentenceTxt
import javax.inject.Inject

class SplashActivity : ACSplashActivity() {

	override val layoutResId = R.layout.splash_activity

	@Inject
	override lateinit var viewModel: SplashViewModel

	private lateinit var disposable: Disposable

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		DaggerSplashComponent.builder().inject(this)
		initQuote()
	}

	override fun onDestroy() {
		disposable.dispose()
		super.onDestroy()
	}

	override fun provideNextActivity(): Intent {
		return Intent(this, SplashActivity::class.java)
	}

	private fun initQuote() {
		disposable = viewModel.getQuote(
				resources.getStringArray(R.array.splash_quoteSentences),
				resources.getStringArray(R.array.splash_quoteAuthors))
				.subscribe(::populateQuote)
	}

	private fun populateQuote(quote: Quote) {
		sentenceTxt.text = getString(R.string.splash_quoteSentence_container, quote.sentence)
		authorTxt.text = getString(R.string.splash_quoteAuthor_container, quote.author)

		// fix auto resized TextView bug with not updated height to the scaled text
		sentenceTxt.doOnPreDraw {
			it.requestLayout()
		}
	}
}
