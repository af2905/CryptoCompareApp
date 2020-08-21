package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_top_coins.view.recyclerViewTopCoins
import kotlinx.android.synthetic.main.fragment_top_coins.view.swipeTopCoinsRefreshLayout
import kotlinx.android.synthetic.main.fragment_top_coins_new.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.adapter.CoinAdapter
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.decoration.DivItemDecoration
import ru.job4j.cryptocompareapp.presentation.item.ICoinClickListener
import ru.job4j.cryptocompareapp.presentation.util.CoinDiffUtilCallback
import ru.job4j.cryptocompareapp.presentation.viewmodel.AppViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TopCoinsFragment : BaseFragment() {
    private lateinit var recycler: RecyclerView
    private val coinAdapter = CoinAdapter()
    private var callbackToDetail: CallbackToDetail? = null
    private val disposeBag = CompositeDisposable()
    private lateinit var infoAboutLastUpdateDisposable: Disposable
    private lateinit var infoAboutLastUpdate: TextView
    private lateinit var swipeTopCoinsRefreshLayout: SwipeRefreshLayout
    private val coinClickListener: ICoinClickListener<Coin> = object : ICoinClickListener<Coin> {
        override fun openDetailInfo(m: Coin) = openCoinDetailInfo(m)
    }
    var appViewModel: AppViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_coins_new, container, false)
        infoAboutLastUpdate = view.txtInfoAboutLastUpdate
        infoAboutLastUpdateDisposable = CompositeDisposable()
        swipeTopCoinsRefreshLayout = view.swipeTopCoinsRefreshLayout
        initRecyclerView(view, coinAdapter)
        loadDataFromCoinViewModel()
        refreshLayoutWithDelay()
        return view
    }

    private fun loadDataFromCoinViewModel() {
        appViewModel?.getLiveDataCoinInfoList()
            ?.observe(
                viewLifecycleOwner,
                Observer {
                    setDataInAdapter(coinAdapter, it)
                    swipeTopCoinsRefreshLayout.isRefreshing = true
                    timerForRefresh(500, TimeUnit.MILLISECONDS)
                    refreshTxtInfoAboutLastUpdate()
                }
            )
    }

    private fun refreshLayoutWithDelay() {
        swipeTopCoinsRefreshLayout.setOnRefreshListener {
            loadDataFromCoinViewModel()
            timerForRefresh(1, TimeUnit.SECONDS)
        }
    }

    private fun timerForRefresh(delay: Long, timeUnit: TimeUnit) {
        disposeBag.add(
            Completable.timer(delay, timeUnit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { swipeTopCoinsRefreshLayout.isRefreshing = false }
        )
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
            .map { DiffUtil.calculateDiff(CoinDiffUtilCallback(coinAdapter.getCoins(), it)) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { coinAdapter.setCoins(coins) }
            .subscribe { it.dispatchUpdatesTo(coinAdapter) }
        infoAboutLastUpdate.visibility = View.VISIBLE
        disposeBag.add(disposable)
        return disposable
    }

    private fun refreshTxtInfoAboutLastUpdate() {
        infoAboutLastUpdateDisposable.dispose()
        infoAboutLastUpdateDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it.toInt()) {
                    0 -> infoAboutLastUpdate.text = getString(R.string.just_now)
                    10 -> infoAboutLastUpdate.text = getString(R.string.a_few_seconds_ago)
                    60 -> infoAboutLastUpdate.text = getString(R.string.a_minute_ago)
                }
            }
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
        infoAboutLastUpdateDisposable.dispose()
        callbackToDetail = null
    }
}
