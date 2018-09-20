package com.socros.android.app.gitez

import android.content.Context
import android.content.res.Resources
import android.os.LocaleList
import androidx.test.InstrumentationRegistry
import com.socros.android.app.gitez.ResourceTest.ResourceType.ARRAY
import com.socros.android.app.gitez.ResourceTest.ResourceType.STRING
import com.socros.android.lib.util.EnumValueCompanion
import java.util.Locale

//TODO move this class to the util module
abstract class ResourceTest {

	companion object {
		private val RESOURCES_DEFAULT_LOCALE = Locale.ENGLISH
	}

	val targetCtx: Context by lazy { InstrumentationRegistry.getTargetContext() }
	val targetRes: Resources by lazy { targetCtx.resources }

	private val defaultLocale = targetRes.configuration.locales.get(0)

	protected fun getLocalizedContext(languageTag: String): Context {
		val config = targetRes.configuration.apply {
			locales = LocaleList(Locale.forLanguageTag(languageTag))
		}
		val localizedContext = targetCtx.createConfigurationContext(config)
		config.locales = LocaleList(defaultLocale)

		return localizedContext
	}

	protected fun Context.getResourceLocales(resId: Int): Set<String> {
		val config = resources.configuration

		config.locales = LocaleList(RESOURCES_DEFAULT_LOCALE)
		val mainResource = createConfigurationContext(config).getResource(resId)

		val resLocalesSet = hashSetOf(RESOURCES_DEFAULT_LOCALE.language)

		for (localeTag in assets.locales) {
			if (localeTag.isEmpty()) continue

			val locale = Locale.forLanguageTag(localeTag)
			config.locales = LocaleList(locale)
			val localisedResource = createConfigurationContext(config).getResource(resId)
			if (mainResource != localisedResource) {
				resLocalesSet.add(locale.language)
			}
		}
		config.locales = LocaleList(defaultLocale)

		return resLocalesSet
	}

	private fun Context.getResource(resId: Int, vararg args: Any): Resource {
		val res = resources
		val resType = ResourceType.fromValue(res.getResourceTypeName(resId))
		val resName = res.getResourceName(resId)
		val value = when (resType) {
			ARRAY -> res.getArray(resId)
			STRING -> res.getString(resId, args)
			else -> throw UnsupportedOperationException("${res.getResourceTypeName(resId)} aren't supported")
		}

		return Resource(resId, resType, resName, value)
	}

	private fun Resources.getArray(resId: Int): Array<*> {
		val array = getStringArray(resId)
		return if (array.isEmpty() || array.first() != null) array
		else getIntArray(resId).toTypedArray()
	}

	private data class Resource(
			val resId: Int,
			val type: ResourceType,
			val name: String,
			val value: Any) {

		override fun hashCode(): Int {
			return value.hashCode()
		}

		override fun equals(other: Any?) =
				if (other is Resource) {
					when (type) {
						ARRAY -> (value as Array<*>) contentEquals (other.value as Array<*>)
						else -> value == other.value
					}
				} else super.equals(other)
	}

	private enum class ResourceType(val typeName: String) {
		ARRAY("array"),
		BOOLEAN("bool"),
		COLOR("color"),
		DIMEN("dimen"),
		INTEGER("integer"),
		PLURAL("plural"),
		STRING("string");

		companion object : EnumValueCompanion<String, ResourceType>(
				ResourceType.values().associateBy(
						ResourceType::typeName))
	}

}
