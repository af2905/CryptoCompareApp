package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.job4j.cryptocompareapp.App
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.view.viewmodel.CoinViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    var coinViewModel: CoinViewModel? = null
    @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createDaggerDependencies()
        coinViewModel?.getTopCoinsInfo()
    }

    private fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    private fun createDaggerDependencies() {
        injectDependency((application as App).getViewModelComponent())
    }
}