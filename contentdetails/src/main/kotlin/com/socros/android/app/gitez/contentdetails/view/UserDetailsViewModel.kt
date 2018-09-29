package com.socros.android.app.gitez.contentdetails.view

import androidx.lifecycle.ViewModel
import com.socros.android.app.gitez.base.view.DataStatus
import com.socros.android.app.gitez.base.view.DataStatus.Error.ServerError
import com.socros.android.app.gitez.contentdetails.R
import com.socros.android.app.gitez.contentdetails.data.ContentDetailsRepository
import com.socros.android.app.gitez.contentdetails.data.UserDetails
import com.socros.android.lib.repository.Resource
import com.socros.android.lib.util.composeAsync
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(private val contentDetailsRepository: ContentDetailsRepository)
	: ViewModel() {

	private val detailsResultsSubject = BehaviorSubject.create<UserDetails>()
	val detailsResults: Observable<UserDetails> = detailsResultsSubject

	private val searchResultsStatusSubject = BehaviorSubject.create<DataStatus>()
	val detailsResultsStatus: Observable<DataStatus> = searchResultsStatusSubject

	private var disposable: Disposable? = null

	fun initUserDetails(username: String) {
		disposable?.dispose()
		disposable = contentDetailsRepository.getUser(username)
				.composeAsync()
				.subscribe { result ->
					result.data?.let { detailsResultsSubject.onNext(it) }

					val hasData = result.data != null
					searchResultsStatusSubject.onNext(
							if (result is Resource.ServerError) {
								ServerError(hasData, detailsStringRes = R.string.userDetails_serverError_details)

							} else DataStatus.fromRepositoryResource(result, hasData)
					)
				}
	}

	override fun onCleared() {
		disposable?.dispose()
	}

}
