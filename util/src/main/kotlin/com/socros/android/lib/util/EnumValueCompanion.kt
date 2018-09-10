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
 *
 * `companion object : EnumValueCompanion<YourValueType, YourEnumClass>
 *    (YourEnumClass.values().associateBy(YourEnumClass::valueField))`
 */
open class EnumValueCompanion<in Value, out EnumType : Enum<*>?> {

	private val valueMap: Map<Value, EnumType>
	private val defaultDefined: Boolean
	private val default: EnumType?

	@Deprecated("Possible dangerous operation! Try use constructor with the specified default!")
	constructor(valueMap: Map<Value, EnumType>) {
		this.valueMap = valueMap
		this.defaultDefined = false
		this.default = null
	}

	constructor(valueMap: Map<Value, EnumType>, default: EnumType) {
		this.valueMap = valueMap
		this.defaultDefined = true
		this.default = default
	}

	@Suppress("UNCHECKED_CAST")
	@Throws(NoSuchElementException::class)
	fun fromValue(type: Value): EnumType =
			valueMap[type]
					?: if (defaultDefined) (default as EnumType)
					else throw NoSuchElementException("Enum not found for $type value!")

}
