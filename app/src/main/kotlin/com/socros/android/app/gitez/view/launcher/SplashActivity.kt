package com.socros.android.app.gitez.view.launcher

import android.content.Intent
import android.os.Bundle
import com.socros.android.app.base.view.launcher.BaseSplashActivity
import com.socros.android.app.gitez.R
import com.socros.android.lib.util.afterMeasured
import com.socros.android.lib.util.random
import kotlinx.android.synthetic.main.activity_splash.authorTxt
import kotlinx.android.synthetic.main.activity_splash.quoteTxt

class SplashActivity : BaseSplashActivity<Any>() {

	override val layoutResId = R.layout.activity_splash
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
		quoteTxt.afterMeasured {
			quoteTxt.requestLayout()
		}
	}

}
