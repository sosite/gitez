package com.socros.android.lib.androidcore.di.viewmodel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.reflect.KClass

@MapKey
@Target(FUNCTION)
annotation class ViewModelKey(val value: KClass<out ViewModel>)
