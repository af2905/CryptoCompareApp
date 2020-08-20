package ru.job4j.cryptocompareapp.di.component

import dagger.Component
import ru.job4j.cryptocompareapp.di.module.ViewModelModule
import ru.job4j.cryptocompareapp.di.scope.ViewModelScope
import ru.job4j.cryptocompareapp.presentation.view.activity.MainActivity
import ru.job4j.cryptocompareapp.presentation.view.fragment.DetailCoinFragment
import ru.job4j.cryptocompareapp.presentation.view.fragment.NewsArticlesFragment
import ru.job4j.cryptocompareapp.presentation.view.fragment.TopCoinsFragment


@ViewModelScope
@Component(modules = [ViewModelModule::class], dependencies = [RepositoryComponent::class])
interface ViewModelComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: TopCoinsFragment)
    fun inject(fragment: DetailCoinFragment)
    fun inject(fragment: NewsArticlesFragment)
}
