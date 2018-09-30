package com.socros.android.lib.androidcore.di

import javax.inject.Scope

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * {@link AppComponent} is a scoped component ({@code @Singleton}, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 *
 * @see <a href="https://github.com/googlesamples/android-architecture-components">Android Architecture Components samples</a>
 */
@Scope
@Retention
annotation class ActivityScope
