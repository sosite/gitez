package com.socros.android.app.gitez

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashQuotesTest : ResourceTest() {

	lateinit var quotesLocales: Array<String>
	lateinit var authorsLocales: Array<String>

	@Before
	fun init() {
		quotesLocales = targetCtx.getResourceLocales(R.array.splash_quotes).toTypedArray()
		authorsLocales = targetCtx.getResourceLocales(R.array.splash_quoteAuthors).toTypedArray()
	}

	@Test
	fun quotesWithAuthorsLocalisationEqualityTest() {
		assert(quotesLocales contentEquals authorsLocales)
	}

	@Test
	fun quotesWithAuthorsMatchEqualityTest() {
		for (locale in quotesLocales) {
			val res = getLocalizedContext(locale).resources
			val quotes = res.getStringArray(R.array.splash_quotes)
			val authors = res.getStringArray(R.array.splash_quoteAuthors)
			assertEquals(
					"Quotes (${quotes.size}) aren't match authors (${authors.size}) array size for \"$locale\" language!",
					quotes.size, authors.size)
		}
	}

}
