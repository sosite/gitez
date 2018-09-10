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

}
