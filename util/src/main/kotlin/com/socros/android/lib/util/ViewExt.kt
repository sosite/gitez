package com.socros.android.lib.util

import android.view.View
import android.view.ViewStub
import androidx.annotation.IdRes

/* MAIN UTILS */

enum class Visibility(val intValue: Int) {
	GONE(View.GONE),
	INVISIBLE(View.INVISIBLE),
	VISIBLE(View.VISIBLE);

	companion object : EnumValueCompanion<Int, Visibility>(
			Visibility.values().associateBy(Visibility::intValue), GONE)
}

inline var View.visible: Boolean
	get() = visibility == View.VISIBLE
	set(value) {
		visibility = if (value) View.VISIBLE else View.GONE
	}

inline var View.visibilityEnum: Visibility
	get() = Visibility.fromValue(visibility)
	set(value) {
		visibility = value.intValue
	}

val View.hasTag: Boolean
	get() = tag != null


val ViewStub.inflated: Boolean
	get() = parent == null

fun View.traverseUpToFindParent(@IdRes id: Int): View? {
	return traverseUpToFindParent { this.id == id }
}

fun View.traverseUpToFindParentOrThrow(@IdRes id: Int): View {
	return traverseUpToFindParent { this.id == id }
			?: throw IllegalArgumentException("Can't find parent with specified id!")
}

tailrec fun View?.traverseUpToFindParent(predicate: View.() -> Boolean): View? {
	return if (this == null || predicate(this)) this
	else (parent as? View).traverseUpToFindParent(predicate)
}

fun View?.traverseUpToFindParentOrThrow(predicate: View.() -> Boolean): View {
	return this?.traverseUpToFindParent(predicate)
			?: throw IllegalArgumentException("Can't find parent with specified predicate!")
}
