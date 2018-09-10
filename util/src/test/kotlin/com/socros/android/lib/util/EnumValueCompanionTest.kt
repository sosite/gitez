package com.socros.android.lib.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class EnumValueCompanionTest {

	@Test
	fun enumFromCorrectValueWithSpecifiedDefault() {
		assertEquals(EnumWithSpecifiedDefault.HAN_SOLO, EnumWithSpecifiedDefault.fromValue("solo"))
		assertEquals(EnumWithSpecifiedDefault.LEIA_ORGANA, EnumWithSpecifiedDefault.fromValue("leia"))
		assertEquals(EnumWithSpecifiedDefault.YODA, EnumWithSpecifiedDefault.fromValue("yoda"))
	}

	@Test
	fun enumFromWrongValueWithSpecifiedDefault() {
		assertEquals(EnumWithSpecifiedDefault.YODA, EnumWithSpecifiedDefault.fromValue("jabba"))
	}

	@Test
	fun enumFromCorrectValueWithNullWhenNotFound() {
		assertEquals(EnumWithNullWhenNotFound.HAN_SOLO, EnumWithNullWhenNotFound.fromValue("solo"))
		assertEquals(EnumWithNullWhenNotFound.LEIA_ORGANA, EnumWithNullWhenNotFound.fromValue("leia"))
		assertEquals(EnumWithNullWhenNotFound.YODA, EnumWithNullWhenNotFound.fromValue("yoda"))
	}

	@Test
	fun enumFromValueWithNullWhenNotFound() {
		assertNull(EnumWithNullWhenNotFound.fromValue("jabba"))
	}

	@Test
	fun enumFromCorrectValueWithoutSpecifiedDefault() {
		assertEquals(EnumWithoutDefault.HAN_SOLO, EnumWithoutDefault.fromValue("solo"))
		assertEquals(EnumWithoutDefault.LEIA_ORGANA, EnumWithoutDefault.fromValue("leia"))
		assertEquals(EnumWithoutDefault.YODA, EnumWithoutDefault.fromValue("yoda"))
	}

	@Test(expected = NoSuchElementException::class)
	fun enumFromWrongValueWithoutSpecifiedDefault() {
		EnumWithoutDefault.fromValue("jabba")
	}


	private enum class EnumWithSpecifiedDefault(private val starName: String) {
		HAN_SOLO("solo"),
		LEIA_ORGANA("leia"),
		YODA("yoda");

		companion object : EnumValueCompanion<String, EnumWithSpecifiedDefault>(
				EnumWithSpecifiedDefault.values().associateBy(EnumWithSpecifiedDefault::starName), YODA)
	}

	private enum class EnumWithNullWhenNotFound(private val starName: String) {
		HAN_SOLO("solo"),
		LEIA_ORGANA("leia"),
		YODA("yoda");

		companion object : EnumValueCompanion<String, EnumWithNullWhenNotFound?>(
				EnumWithNullWhenNotFound.values().associateBy(EnumWithNullWhenNotFound::starName), null)
	}

	private enum class EnumWithoutDefault(private val starName: String) {
		HAN_SOLO("solo"),
		LEIA_ORGANA("leia"),
		YODA("yoda");

		companion object : EnumValueCompanion<String, EnumWithoutDefault>(
				EnumWithoutDefault.values().associateBy(EnumWithoutDefault::starName))
	}

}
