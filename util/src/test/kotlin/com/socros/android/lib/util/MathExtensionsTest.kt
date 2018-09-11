package com.socros.android.lib.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MathExtensionsTest {

	@get:Rule
	val repeatRule = RepeatRule()

	@Test
	@Repeat(100)
	fun rangeRandomValueTest() {
		val random = (0..10).random()
		assertTrue(random in 0..10)
	}

	@Test
	fun singleValueRangeTest() {
		val random = (10..10).random()
		assertEquals(10, random)
	}

	@Test
	fun decreasedRangeRandomValueTest() {
		@Suppress("EmptyRange")
		val random = (5..-5).random()
		assertEquals(5, random)
	}

}
