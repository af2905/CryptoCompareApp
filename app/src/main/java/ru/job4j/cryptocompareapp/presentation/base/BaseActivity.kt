package ru.job4j.cryptocompareapp.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.job4j.cryptocompareapp.App
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDependencies()
    }

    private fun createDependencies() {
        injectDependency((application as App).getViewModelComponent())
    }

    abstract fun injectDependency(component: ViewModelComponent)

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }
}
