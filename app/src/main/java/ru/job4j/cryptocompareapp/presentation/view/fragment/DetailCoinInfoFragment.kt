package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail_coin_info.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import javax.inject.Inject

class DetailCoinInfoFragment : BaseFragment() {
    var coinViewModel: CoinViewModel? = null
        @Inject set
    lateinit var detailIcon: ImageView
    lateinit var detailArrow: ImageView
    lateinit var detailPctChange24: TextView

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_coin_info, container, false)
        val detailFullName = view.txtDetailFullName
        detailIcon = view.imgDetailIcon
        detailArrow = view.imgDetailArrow
        val detailPrice = view.txtDetailPrice
        val detailChange24 = view.txtDetailChange24
        detailPctChange24 = view.txtDetailChangePct24
        val detailMarketCap = view.txtDetailMarketCap
        val detailHigh24 = view.txtDetailHigh24
        val detailLow24 = view.txtDetailLow24
        val detailTotalVolume24 = view.txtDetailTotalVolume24
        val detailOpen24 = view.txtDetailOpen24
        val detailDirectVol24 = view.txtDetailDirectVol24

        coinViewModel?.getLiveDataSelectedCoin()?.observe(viewLifecycleOwner, Observer {
            val url = it.displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl()
            loadImg(url)
            detailFullName.text = it.coinBasicInfo.fullName
            detailPrice.text = it.displayCoinPriceInfo.coinPriceInfo?.price
            detailChange24.text = it.displayCoinPriceInfo.coinPriceInfo?.change24Hour
            detailPctChange24.text =
                String.format("%% %s", it.displayCoinPriceInfo.coinPriceInfo?.changePct24Hour)
            detailMarketCap.text = it.displayCoinPriceInfo.coinPriceInfo?.mktCap
            detailHigh24.text = it.displayCoinPriceInfo.coinPriceInfo?.high24Hour
            detailLow24.text = it.displayCoinPriceInfo.coinPriceInfo?.low24Hour
            detailTotalVolume24.text = it.displayCoinPriceInfo.coinPriceInfo?.totalVolume24H
            detailOpen24.text = it.displayCoinPriceInfo.coinPriceInfo?.open24Hour
            detailDirectVol24.text = it.displayCoinPriceInfo.coinPriceInfo?.topTierVolume24Hour
            checkPercentageChangesAndSetArrow(view, it)
        })
        return view
    }

    private fun loadImg(imgUrl: String?) {
        activity?.applicationContext?.let {
            Glide.with(it).load(imgUrl).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error)
            ).transition(DrawableTransitionOptions.withCrossFade()).into(detailIcon)
        }
    }

    private fun checkPercentageChangesAndSetArrow(view: View, coin: Coin) {
        val change24Hour = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        if (change24Hour != null) {
            if (change24Hour.contains('-')) {
                detailArrow.setImageDrawable(
                    resources.getDrawable(R.drawable.ic_arrow_down, view.context.theme)
                )
                detailPctChange24.setTextColor(
                    resources.getColor(
                        R.color.colorErrorRed,
                        view.context.theme
                    )
                )
            } else {
                detailArrow.setImageDrawable(
                    resources.getDrawable(R.drawable.ic_arrow_up, view.context.theme)
                )
                detailPctChange24.setTextColor(
                    resources.getColor(
                        R.color.colorBrightGreen,
                        view.context.theme
                    )
                )
            }
        }
    }
}

