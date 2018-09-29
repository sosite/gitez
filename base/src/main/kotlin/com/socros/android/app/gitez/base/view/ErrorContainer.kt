package com.socros.android.app.gitez.base.view

import android.view.View
import com.socros.android.lib.androidcore.view.LayoutContainer
import com.socros.android.lib.util.visible
import kotlinx.android.synthetic.main.error_container.errorBtn
import kotlinx.android.synthetic.main.error_container.errorDetailsTxt
import kotlinx.android.synthetic.main.error_container.errorHeaderTxt
import org.jetbrains.anko.textResource

class ErrorContainer(errorContainer: View, private val btnClickAction: (DataStatus.Error) -> Unit)
	: LayoutContainer(errorContainer) {

	private var dataStatus: DataStatus.Error? = null

	init {
		errorBtn.setOnClickListener { dataStatus?.let { error -> btnClickAction(error) } }
	}

	fun updateVisibility(dataStatus: DataStatus, actionIfHasData: (DataStatus.Error) -> Unit) {
		containerView.visible =
				dataStatus is DataStatus.Error
				&& !dataStatus.hasData

		this.dataStatus = (dataStatus as? DataStatus.Error)?.also { error ->
			if (dataStatus.hasData) actionIfHasData(error)
			else {
				errorHeaderTxt.textResource = error.headerStringRes
				errorDetailsTxt.textResource = error.detailsStringRes
				errorBtn.textResource = error.buttonStringRes
			}
		}
	}
}
