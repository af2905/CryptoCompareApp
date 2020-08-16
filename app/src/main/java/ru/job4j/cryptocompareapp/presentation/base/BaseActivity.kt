package ru.job4j.cryptocompareapp.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.job4j.cryptocompareapp.App
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDaggerDependencies()
    }

    private fun createDaggerDependencies() =
        injectDependency((application as App).getViewModelComponent())

    abstract fun injectDependency(component: ViewModelComponent)
}
