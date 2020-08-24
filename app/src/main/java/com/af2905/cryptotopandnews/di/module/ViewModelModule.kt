package com.af2905.cryptotopandnews.di.module

import android.app.Application
import com.af2905.cryptotopandnews.di.scope.ViewModelScope
import com.af2905.cryptotopandnews.presentation.viewmodel.AppViewModel
import com.af2905.cryptotopandnews.repository.AppRepository
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(private val application: Application) {
    @Provides
    @ViewModelScope
    fun providesAppViewModel(repository: AppRepository): AppViewModel {
        return AppViewModel(application, repository)
    }
}
