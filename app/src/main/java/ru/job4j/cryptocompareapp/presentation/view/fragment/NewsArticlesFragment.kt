package ru.job4j.cryptocompareapp.presentation.view.fragment

import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.AppViewModel
import javax.inject.Inject

class NewsArticlesFragment : BaseFragment() {
    var appViewModel: AppViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }
}