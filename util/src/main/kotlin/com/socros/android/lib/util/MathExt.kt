package com.socros.android.lib.util

import java.util.Random

/**
 * Generate random number from specified range
 */
fun ClosedRange<Int>.random() =
		if (endInclusive <= start) start
		else Random().nextInt(endInclusive - start) + start


/**
 * `it == value`, *equals*
 */
inline fun <T> T.eq(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> {
	if (this == value) action(this)
}

/**
 * `it == value`, *equals*
 */
inline fun <T> T.equals(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> = eq(value, action)

/**
 * `it != value`, *not equals*
 */
inline fun <T> T.ne(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> {
	if (this != value) action(this)
}

/**
 * `it != value`, *equals*
 */
inline fun <T> T.notEquals(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> = ne(value, action)

/**
 * `it > value`, *greater than*
 */
inline fun <T> T.gt(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> {
	if (this > value) action(this)
}

/**
 * `it > value`, *greater than*
 */
inline fun <T> T.greaterThan(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> = gt(value, action)

/**
 * `it >= value`, *greater than or equal*
 */
inline fun <T> T.ge(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> {
	if (this >= value) action(this)
}

/**
 * `it >= value`, *greater than or equal*
 */
inline fun <T> T.greaterThanOrEqual(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> =
		ge(value, action)

/**
 * `it < value`, *less than*
 */
inline fun <T> T.lt(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> {
	if (this < value) action(this)
}

/**
 * `it < value`, *less than*
 */
inline fun <T> T.lessThan(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> = lt(value, action)

/**
 * `it <= value`, *less than or equal*
 */
inline fun <T> T.le(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> {
	if (this <= value) action(this)
}

/**
 * `it <= value`, *less than or equal*
 */
inline fun <T> T.lessThanOrEqual(value: T, action: (T) -> Unit) where T : Number, T : Comparable<T> = le(value, action)
