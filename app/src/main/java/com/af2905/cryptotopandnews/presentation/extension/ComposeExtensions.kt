package com.af2905.cryptotopandnews.presentation.extension

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
inline fun <reified T : ViewModel> viewModel(
    key: String,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "LocalViewModelStoreOwner is not exist"
    },
    viewModelFactory: () -> ViewModelProvider.Factory
): T = ViewModelProvider(viewModelStoreOwner.viewModelStore, viewModelFactory())[key, T::class.java]