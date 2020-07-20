package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import android.view.View
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
        val coinAdapter = CoinAdapter()
        initRecyclerView(coinAdapter)
        coinViewModel?.getLiveDataCoinInfoList()
            ?.observe(this, Observer { setDataInAdapter(coinAdapter, it) })
        swipeRefreshLayout.setOnRefreshListener {
            coinViewModel?.getLiveDataCoinInfoList()
                ?.observe(this, Observer { setDataInAdapter(coinAdapter, it) })
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initRecyclerView(coinAdapter: CoinAdapter) {
        recycler = rvCoinPriceList
        coinAdapter.listener = itemClickListener
        recycler.adapter = coinAdapter

        val dividerItemDecoration =
            DividerItemDecoration(recycler.context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(
            this.resources.getDrawable(R.drawable.vertical_divider, applicationContext.theme)
        )
        recycler.addItemDecoration(dividerItemDecoration)
    }

    private fun setDataInAdapter(coinAdapter: CoinAdapter, newList: List<Coin>) {
        coinAdapter.setData(newList)
    }

    private val itemClickListener = object : ICoinItemClickListener<Coin> {
        override fun openDetail(m: Coin) {
            Toast.makeText(this@MainActivity, "DetailClick", Toast.LENGTH_SHORT).show()
        }

        override fun onClick(v: View?) {

        }
    }

    private fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    private fun createDaggerDependencies() {
        injectDependency((application as App).getViewModelComponent())
    }
}