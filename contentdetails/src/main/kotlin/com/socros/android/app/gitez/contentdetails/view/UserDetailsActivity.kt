package com.socros.android.app.gitez.contentdetails.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.socros.android.app.gitez.base.glide.GlideApp
import com.socros.android.app.gitez.base.view.BaseActivity
import com.socros.android.app.gitez.base.view.DataStatus.InProgress
import com.socros.android.app.gitez.base.view.ErrorContainer
import com.socros.android.app.gitez.contentdetails.R
import com.socros.android.app.gitez.contentdetails.data.UserDetails
import com.socros.android.app.gitez.contentdetails.di.DaggerUserDetailsActivityComponent
import com.socros.android.lib.util.textOrHide
import com.socros.android.lib.util.visible
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.user_details_activity.avatarImg
import kotlinx.android.synthetic.main.user_details_activity.bioTxt
import kotlinx.android.synthetic.main.user_details_activity.followGroup
import kotlinx.android.synthetic.main.user_details_activity.followersValueTxt
import kotlinx.android.synthetic.main.user_details_activity.followingValueTxt
import kotlinx.android.synthetic.main.user_details_activity.includedErrorContainer
import kotlinx.android.synthetic.main.user_details_activity.loginPlaceholder
import kotlinx.android.synthetic.main.user_details_activity.loginTxt
import kotlinx.android.synthetic.main.user_details_activity.namePlaceholder
import kotlinx.android.synthetic.main.user_details_activity.nameTxt
import kotlinx.android.synthetic.main.user_details_activity.toolbar
import org.jetbrains.anko.toast
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
	private lateinit var errorContainer: ErrorContainer

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
		supportActionBar?.apply {
			setDisplayHomeAsUpEnabled(true)
			setDisplayShowTitleEnabled(false)
		}

		errorContainer = ErrorContainer(includedErrorContainer) { detailsViewModel.initUserDetails(username) }
		bindToDetailsResults()
		bindToDetailsStatus()

		detailsViewModel.initUserDetails(username)
	}

	override fun onDestroy() {
		disposable.dispose()
		super.onDestroy()
	}

	private fun bindToDetailsResults() {
		detailsViewModel.detailsResults.subscribe(::bindDetails)
				.addTo(disposable)
	}

	private fun bindDetails(details: UserDetails) {
		namePlaceholder.visible = false
		loginPlaceholder.visible = false

		details.run {
			GlideApp.with(this@UserDetailsActivity)
					.load(avatarURl)
					.circleCrop()
					.into(avatarImg)

			if (name != null) {
				nameTxt.text = name
				loginTxt.text = login

			} else {
				nameTxt.text = login
				loginTxt.visible = false
			}

			bioTxt.textOrHide = bio

			followGroup.visible = true
			followersValueTxt.text = followers.toString()
			followingValueTxt.text = following.toString()
		}
	}

	private fun bindToDetailsStatus() {
		detailsViewModel.detailsResultsStatus.subscribe {
			if (it is InProgress && !it.hasData) {
				namePlaceholder.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
				loginPlaceholder.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink))
			} else {
				namePlaceholder.clearAnimation()
				loginPlaceholder.clearAnimation()
			}

			errorContainer.updateVisibility(it) { error -> toast(error.detailsStringRes) }

		}.addTo(disposable)
	}

}
