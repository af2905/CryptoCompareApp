package com.af2905.cryptotopandnews.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.di.component.ViewModelComponent
import com.af2905.cryptotopandnews.presentation.adapter.CoinAdapter
import com.af2905.cryptotopandnews.presentation.base.BaseFragment
import com.af2905.cryptotopandnews.presentation.decoration.DivItemDecoration
import com.af2905.cryptotopandnews.presentation.item.IClickListener
import com.af2905.cryptotopandnews.presentation.utils.CoinDiffUtilCallback
import com.af2905.cryptotopandnews.presentation.viewmodel.AppViewModel
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_top_coins.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TopCoinsFragment : BaseFragment() {
    private lateinit var recycler: RecyclerView
    private val coinAdapter = CoinAdapter()
    private var callbackToDetail: CallbackToCoinDetail? = null
    private val disposeBag = CompositeDisposable()
    private lateinit var swipeTopCoinsRefreshLayout: SwipeRefreshLayout
    private val clickListener: IClickListener<Coin> = object : IClickListener<Coin> {
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
        val view = inflater.inflate(R.layout.fragment_top_coins, container, false)
        swipeTopCoinsRefreshLayout = view.swipeTopCoinsRefreshLayout
        initRecyclerView(view)
        setBehaviorWhenLoadingError()
        loadDataFromViewModel()
        refreshLayoutWithDelay()
        return view
    }

    private fun initRecyclerView(view: View) {
        recycler = view.recyclerViewTopCoins
        coinAdapter.setClickListener(clickListener)
        recycler.adapter = coinAdapter
        recycler.addItemDecoration(DivItemDecoration(16, 8))
    }

    private fun setBehaviorWhenLoadingError() {
        appViewModel?.getLiveDataErrorWhenLoadingData()?.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> swipeTopCoinsRefreshLayout.isRefreshing = true
                false -> swipeTopCoinsRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun loadDataFromViewModel() {
        appViewModel?.getLiveDataCoinInfoList()?.observe(
            viewLifecycleOwner, Observer { setDataInAdapter(coinAdapter, it) }
        )
    }

    private fun setDataInAdapter(coinAdapter: CoinAdapter, coins: List<Coin>): Disposable {
        val listOfCoins: Observable<List<Coin>> = Observable.fromArray(coins)
        val disposable = listOfCoins
            .map { DiffUtil.calculateDiff(CoinDiffUtilCallback(coinAdapter.getCoins(), it)) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { coinAdapter.setCoins(coins) }
            .subscribe { it.dispatchUpdatesTo(coinAdapter) }
        disposeBag.add(disposable)
        return disposable
    }

    private fun refreshLayoutWithDelay() {
        swipeTopCoinsRefreshLayout.setOnRefreshListener {
            loadDataFromViewModel()
            disposeBag.add(
                Completable.timer(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        swipeTopCoinsRefreshLayout.isRefreshing = false
                        val animId = R.anim.layout_animation_fall_down
                        recycler.layoutAnimation =
                            AnimationUtils.loadLayoutAnimation(context, animId)
                        Toast.makeText(context, R.string.just_updated, Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }

    fun openCoinDetailInfo(coin: Coin) {
        callbackToDetail?.openCoinDetailClick(coin)
    }

    interface CallbackToCoinDetail {
        fun openCoinDetailClick(coin: Coin)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callbackToDetail = context as CallbackToCoinDetail
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        callbackToDetail = null
    }
}
