package com.socros.android.lib.androidcore.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.socros.android.lib.androidcore.ACApp
import com.socros.android.lib.androidcore.di.AppProvider

abstract class ACFragment : Fragment(), AppProvider {

	/**
	 * You can pass a `null` value if your fragment doesn't need to draw itself
	 */
	protected abstract val layoutResId: Int?

	final override fun provideApp() = activity?.application as ACApp

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return layoutResId?.let {
			inflater.inflate(it, container, false)
		}
	}

}
