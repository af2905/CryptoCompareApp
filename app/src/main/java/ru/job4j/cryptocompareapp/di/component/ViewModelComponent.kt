package ru.job4j.cryptocompareapp.di.component

import dagger.Component
import ru.job4j.cryptocompareapp.di.module.ViewModelModule
import ru.job4j.cryptocompareapp.di.scope.ViewModelScope
import ru.job4j.cryptocompareapp.presentation.view.activity.MainActivity

@ViewModelScope
@Component(modules = [ViewModelModule::class], dependencies = [RepositoryComponent::class])
interface ViewModelComponent {
    fun inject(activity: MainActivity)
}