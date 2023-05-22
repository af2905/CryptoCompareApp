package com.af2905.cryptotopandnews.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Provider

class ViewModelFactory(
    private val creators: Map<String, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val creator = creators[extras[ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY]]
            ?: error("unknown view model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }
}