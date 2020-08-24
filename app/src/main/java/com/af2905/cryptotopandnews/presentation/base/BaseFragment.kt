package com.af2905.cryptotopandnews.presentation.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.af2905.cryptotopandnews.App
import com.af2905.cryptotopandnews.di.component.ViewModelComponent

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
