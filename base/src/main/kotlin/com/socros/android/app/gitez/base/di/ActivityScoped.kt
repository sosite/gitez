package com.socros.android.app.gitez.base.di

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * In Dagger, an unscoped component cannot depend on a scoped component. As
 * {@link AppComponent} is a scoped component ({@code @Singleton}, we create a custom
 * scope to be used by all fragment components. Additionally, a component with a specific scope
 * cannot have a sub component with the same scope.
 *
 * See Also: [Android Architecture Components samples](https://github.com/googlesamples/android-architecture-components)
 */
@Scope
@Retention(RUNTIME)
annotation class ActivityScoped
