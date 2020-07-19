package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_coin_price_list.*
import ru.job4j.cryptocompareapp.App
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.adapter.CoinAdapter
import ru.job4j.cryptocompareapp.presentation.item.ICoinItemClickListener
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    var coinViewModel: CoinViewModel? = null
        @Inject set

    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_coin_price_list)
        createDaggerDependencies()
        coinViewModel?.getLiveDataCoinInfoList()
            ?.observe(this, Observer { initRecyclerView(it) })

    }

    private fun initRecyclerView(coins: List<Coin>) {
        recycler = rvCoinPriceList
        val coinAdapter = CoinAdapter()
        coinAdapter.coinPriceInfoList = coins
        coinAdapter.listener = itemClickListener
        recycler.adapter = coinAdapter
        val dividerItemDecoration =
            DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL)
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private val itemClickListener = object : ICoinItemClickListener<Coin> {
        override fun openDetail(m: Coin) {
            Toast.makeText(this@MainActivity, "DetailClick", Toast.LENGTH_SHORT).show()
        }
    }

    private fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    private fun createDaggerDependencies() {
        injectDependency((application as App).getViewModelComponent())
    }
}