package com.af2905.cryptotopandnews.di.component

import com.af2905.cryptotopandnews.di.module.RepositoryModule
import com.af2905.cryptotopandnews.di.scope.RepositoryScope
import com.af2905.cryptotopandnews.repository.AppRepository
import dagger.Component

@RepositoryScope
@Component(
    modules = [RepositoryModule::class],
    dependencies = [ApiComponent::class, DatabaseComponent::class]
)
interface RepositoryComponent {
    val repository: AppRepository
}
