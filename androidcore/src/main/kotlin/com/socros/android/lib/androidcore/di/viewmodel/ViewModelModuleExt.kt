package com.socros.android.lib.androidcore.di.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

inline fun <reified VM : ViewModel> ViewModelFactory.create(target: FragmentActivity) =
		ViewModelProviders.of(target, this)[VM::class.java]

inline fun <reified VM : ViewModel> ViewModelFactory.create(target: Fragment) =
		ViewModelProviders.of(target, this)[VM::class.java]
