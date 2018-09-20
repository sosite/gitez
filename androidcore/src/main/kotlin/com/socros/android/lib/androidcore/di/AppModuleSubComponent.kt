package com.socros.android.lib.androidcore.di

import dagger.Subcomponent

/**
 * Created by luigipapino on 18/02/2018, previously named BaseSubComponent
 */
@Subcomponent
interface AppModuleSubComponent {
	@Subcomponent.Builder
	interface Builder {
		fun build(): AppModuleSubComponent
	}
}
