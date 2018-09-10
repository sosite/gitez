package com.socros.android.app.gitez

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppBasicTest {
	@Test
	fun useAppContext() {
		val appContext = InstrumentationRegistry.getTargetContext()
		assertEquals("com.socros.android.app.gitez${if (BuildConfig.DEBUG) ".debug" else ""}", appContext.packageName)
	}
}
