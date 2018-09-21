package com.socros.android.lib.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dagger.Lazy

fun Fragment.argumentsOrThrow() =
		arguments
				?: throw RuntimeException("You don't use proper fragment initialization. "
						+ "You must use provided arguments generator!\n"
						+ "Creation of this fragment without specified arguments "
						+ "is not supported to prevent bugs like this.")

fun FragmentActivity.addFragment(fragmentContainerId: Int, fragmentProvider: Lazy<out Fragment>) {
	addFragment(fragmentContainerId) {
		// Get the fragment from dagger
		fragmentProvider.get()
	}
}

fun FragmentActivity.addFragment(fragmentContainerId: Int, builder: () -> Fragment) {
	val fragment: Fragment? = supportFragmentManager.findFragmentById(fragmentContainerId)
	if (fragment == null) {
		supportFragmentManager.inTransaction {
			add(fragmentContainerId, builder())
		}
	}
}

inline fun <reified F : Fragment> createFragment(arguments: Bundle? = null) = createFragment(F::class.java, arguments)

fun <F : Fragment> createFragment(fragmentClass: Class<F>, arguments: Bundle? = null): F {
	try {
		return fragmentClass.newInstance().apply {
			if (arguments != null) {
				this.arguments = arguments
			}
		}

		// TODO: Kotlin doesn't support multi catch right now :/
		// https://discuss.kotlinlang.org/t/does-kotlin-have-multi-catch/486/7
	} catch (e: InstantiationException) {
		throw IllegalArgumentException("Can't create fragment object from " + fragmentClass.simpleName
				+ ". Make sure that it's not an abstract class!")
	} catch (e: IllegalAccessException) {
		throw IllegalArgumentException("Can't create fragment object from " + fragmentClass.simpleName
				+ ". Make sure that it's not an abstract class!")
	}
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
		beginTransaction().func().commit()
