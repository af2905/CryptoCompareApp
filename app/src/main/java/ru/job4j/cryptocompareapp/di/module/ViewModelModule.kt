package ru.job4j.cryptocompareapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.job4j.cryptocompareapp.di.scope.ViewModelScope
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import ru.job4j.cryptocompareapp.repository.AppRepository

@Module
class ViewModelModule(private val application: Application) {
    @Provides
    @ViewModelScope
    fun providesCoinViewModel(repository: AppRepository): CoinViewModel {
        return CoinViewModel(application, repository)
    }
}
