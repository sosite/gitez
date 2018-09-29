package com.socros.android.app.gitez.contentdetails.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.contentdetails.R
import com.socros.android.app.gitez.contentdetails.di.DaggerUserDetailsActivityComponent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.user_details_activity.textTxt
import kotlinx.android.synthetic.main.user_details_activity.toolbar
import javax.inject.Inject

class UserDetailsActivity : BaseActivity() {

	companion object {
		private const val EXTRA_USERNAME = "argUsername"

		fun createIntent(context: Context, username: String): Intent {
			return Intent(context, UserDetailsActivity::class.java).apply {
				putExtra(EXTRA_USERNAME, username)
			}
		}
	}

	override val layoutResId = R.layout.user_details_activity

	@Inject
	lateinit var detailsViewModel: UserDetailsViewModel
	private val disposable = CompositeDisposable()

	private val username: String
		get() = intent.extras?.getString(EXTRA_USERNAME)
				?: throw IllegalArgumentException("You forgot to create activity with specified username!")

	override fun onCreate(savedInstanceState: Bundle?) {
		DaggerUserDetailsActivityComponent.builder().inject(this)
		super.onCreate(savedInstanceState)

		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		initView()
		bindToDetailsResults()
		bindToDetailsStatus()

		if (firstTimeCreated(savedInstanceState)) {
			detailsViewModel.initUserDetails(username)
		}
	}

	override fun onDestroy() {
		disposable.dispose()
		super.onDestroy()
	}

	private fun initView() {}

	private fun bindToDetailsResults() {
		detailsViewModel.detailsResults.subscribe {
			textTxt.text = it.login
		}.addTo(disposable)
	}

	private fun bindToDetailsStatus() {}

}
