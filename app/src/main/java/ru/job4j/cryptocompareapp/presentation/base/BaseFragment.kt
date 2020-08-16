package ru.job4j.cryptocompareapp.presentation.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.job4j.cryptocompareapp.App
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent

abstract class BaseFragment : Fragment() {
    private lateinit var activity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDaggerDependencies()
    }

    private fun createDaggerDependencies() =
        injectDependency((activity.application as App).getViewModelComponent())

    abstract fun injectDependency(component: ViewModelComponent)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }
}
