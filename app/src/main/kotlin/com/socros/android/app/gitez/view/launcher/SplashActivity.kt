package com.socros.android.app.gitez.view.launcher

import android.content.Intent
import android.os.Bundle
import androidx.core.view.doOnPreDraw
import com.socros.android.app.base.view.launcher.CoreSplashActivity
import com.socros.android.app.gitez.R
import com.socros.android.lib.util.random
import kotlinx.android.synthetic.main.splash_activity.authorTxt
import kotlinx.android.synthetic.main.splash_activity.quoteTxt

class SplashActivity : CoreSplashActivity<Any>() {

	override val layoutResId = R.layout.splash_activity
	override val splashMinVisibilityTime = 3_000

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initQuote()
	}

	override fun provideNextActivity(): Intent {
		return Intent(this, SplashActivity::class.java)
	}

	private fun initQuote() {
		val quoteArray = resources.getStringArray(R.array.splash_quotes)
		val randomIndex = (0..quoteArray.size).random()
		val quote = quoteArray[randomIndex]
		val author = resources.getStringArray(R.array.splash_quoteAuthors)[randomIndex]

		quoteTxt.text = getString(R.string.splash_quote, quote)
		authorTxt.text = getString(R.string.splash_author, author)

		// fix auto resized TextView bug with not updated height to the scaled text
		quoteTxt.doOnPreDraw {
			it.requestLayout()
		}
	}

}
