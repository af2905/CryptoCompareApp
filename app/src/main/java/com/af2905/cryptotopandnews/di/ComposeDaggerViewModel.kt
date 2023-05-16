package com.af2905.cryptotopandnews.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> daggerViewModel(
   key: String? = null,
   crossinline viewModelInstanceCreator: () -> T
): T = viewModel(
       modelClass = T::class.java,
       key = key,
       factory = object : ViewModelProvider.Factory {
           override fun <T : ViewModel> create(modelClass: Class<T>): T {
               return viewModelInstanceCreator() as T
           }
       }
   )