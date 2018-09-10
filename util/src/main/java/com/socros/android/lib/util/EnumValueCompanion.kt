package com.socros.android.lib.util

/**
 * Companion class to faster gather enums from it's value with using values map
 *
 * Sample usages:
 *
 * `companion object : EnumValueCompanion<YourValueType, YourEnumClass>
 *    (YourEnumClass.values().associateBy(YourEnumClass::valueField), YourEnumClass.DEFAULT_VALUE)`
 *
 * `companion object : EnumValueCompanion<YourValueType, YourEnumClass?>
 *    (YourEnumClass.values().associateBy(YourEnumClass::valueField), null)`
 */
open class EnumValueCompanion<in Value, EnumType : Enum<*>?>(
		private val valueMap: Map<Value, EnumType>,
		private val default: EnumType) {

	@Throws(NoSuchElementException::class)
	fun fromValue(type: Value): EnumType = valueMap[type] ?: default
}
