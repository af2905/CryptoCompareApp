package ru.job4j.cryptocompareapp.di.component

import dagger.Component
import ru.job4j.cryptocompareapp.di.module.RepositoryModule
import ru.job4j.cryptocompareapp.di.scope.RepositoryScope
import ru.job4j.cryptocompareapp.repository.AppRepository

@RepositoryScope
@Component(
    modules = [RepositoryModule::class],
    dependencies = [ApiComponent::class, DatabaseComponent::class]
)
interface RepositoryComponent {
    val repository: AppRepository
}