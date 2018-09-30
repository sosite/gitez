package com.socros.android.lib.util

@Retention()
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER,
		AnnotationTarget.ANNOTATION_CLASS)
annotation class Repeat(val value: Int)
