package com.socros.android.app.gitez.base.di

import dagger.Module

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be [ApplicationComponent]. The beautiful part about this setup is that you never need to tell ApplicationComponent
 * that it is going to have all these subcomponents nor do you need to tell these subcomponents that ApplicationComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create subcomponents for us.
 *
 * @see <a href="https://github.com/googlesamples/android-architecture-components">Android Architecture Components samples</a>
 */
@Module
abstract class ActivityBindingModule
