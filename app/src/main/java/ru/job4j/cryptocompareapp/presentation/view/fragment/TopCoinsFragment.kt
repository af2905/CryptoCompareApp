package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_top_coins.*
import kotlinx.android.synthetic.main.fragment_top_coins.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.adapter.CoinAdapter
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.decoration.DivItemDecoration
import ru.job4j.cryptocompareapp.presentation.item.ICoinClickListener
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TopCoinsFragment : BaseFragment() {
    lateinit var recycler: RecyclerView
    private val coinAdapter = CoinAdapter()
    private var callbackToDetail: CallbackToDetail? = null
    private val disposeBag = CompositeDisposable()
    private val coinClickListener: ICoinClickListener<Coin> = object : ICoinClickListener<Coin> {
        override fun openDetailInfo(m: Coin) = openCoinDetailInfo(m)
    }
    var coinViewModel: CoinViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_coins, container, false)
        initRecyclerView(view, coinAdapter)
        coinViewModel?.getLiveDataCoinInfoList()
            ?.observe(viewLifecycleOwner, Observer { setDataInAdapter(coinAdapter, it) })
        view.swipeTopCoinsRefreshLayout.setOnRefreshListener {
            coinViewModel?.getLiveDataCoinInfoList()
                ?.observe(viewLifecycleOwner, Observer {
                    setDataInAdapter(coinAdapter, it)
                })
            disposeBag.add(Completable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { swipeTopCoinsRefreshLayout.isRefreshing = false })
        }
        return view
    }

    private fun initRecyclerView(view: View, coinAdapter: CoinAdapter) {
        recycler = view.recyclerViewTopCoins
        coinAdapter.setCoinClickListener(coinClickListener)
        recycler.adapter = coinAdapter
        recycler.addItemDecoration(DivItemDecoration(16, 8))
    }

    private fun setDataInAdapter(coinAdapter: CoinAdapter, coins: List<Coin>): Disposable {
        val listOfCoins: Observable<List<Coin>> = Observable.fromArray(coins)
        val disposable = listOfCoins
            .map { DiffUtil.calculateDiff(CoinDiffUtilCallback(coinAdapter.coinList, it)) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { coinAdapter.coinList = coins as MutableList<Coin> }
            .subscribe { it.dispatchUpdatesTo(coinAdapter) }
        disposeBag.add(disposable)
        return disposable
    }

    fun openCoinDetailInfo(coin: Coin) {
        callbackToDetail?.openCoinDetailClick(coin)
    }

    interface CallbackToDetail {
        fun openCoinDetailClick(coin: Coin)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callbackToDetail = context as CallbackToDetail
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        callbackToDetail = null
    }
}