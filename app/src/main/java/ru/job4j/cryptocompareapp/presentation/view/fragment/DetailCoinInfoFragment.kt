package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_detail_coin_info.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.server.GlideClient
import javax.inject.Inject

class DetailCoinInfoFragment : BaseFragment() {
    private lateinit var detailFullName: TextView
    private lateinit var detailName: TextView
    private lateinit var detailPrice: TextView
    private lateinit var detailChange24: TextView
    private lateinit var detailMarketCap: TextView
    private lateinit var detailHigh24: TextView
    private lateinit var detailLow24: TextView
    private lateinit var detailTotalVolume24: TextView
    private lateinit var detailOpen24: TextView
    private lateinit var detailIcon: ImageView
    private lateinit var detailArrow: ImageView
    private lateinit var detailPctChange24: TextView
    private lateinit var detailInfographic1: ImageView
    var coinViewModel: CoinViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_coin_info_new, container, false)
        initViews(view)
        coinViewModel?.getLiveDataSelectedCoin()?.observe(
            viewLifecycleOwner,
            Observer {
                val imgUrl = it.displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl()
                downloadImage(imgUrl)
                setDataToViews(it)
                checkPercentageChangesAndSetArrow(view, it)
            }
        )
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            detailFullName = txtDetailFullName
            detailName = txtDetailName
            detailIcon = imgDetailIcon
            detailArrow = imgDetailArrow
            detailPrice = txtDetailPrice
            detailChange24 = txtDetailChange24
            detailPctChange24 = txtDetailChangePct24
            detailMarketCap = txtDetailMarketCap
            detailHigh24 = txtDetailHigh24
            detailLow24 = txtDetailLow24
            detailTotalVolume24 = txtDetailTotalVolume24
            detailOpen24 = txtDetailOpen24
            detailInfographic1 = imgDetailInfographic1
        }
    }

    private fun setDataToViews(coin: Coin) {
        with(coin) {
            detailFullName.text = coinBasicInfo.fullName
            detailName.text = coinBasicInfo.name
            detailPrice.text = displayCoinPriceInfo.coinPriceInfo?.price
            detailChange24.text = displayCoinPriceInfo.coinPriceInfo?.change24Hour
            detailPctChange24.text =
                String.format("%% %s", displayCoinPriceInfo.coinPriceInfo?.changePct24Hour)
            detailMarketCap.text = displayCoinPriceInfo.coinPriceInfo?.mktCap
            detailHigh24.text = displayCoinPriceInfo.coinPriceInfo?.high24Hour
            detailLow24.text = displayCoinPriceInfo.coinPriceInfo?.low24Hour
            detailTotalVolume24.text = displayCoinPriceInfo.coinPriceInfo?.totalVolume24H
            detailOpen24.text = displayCoinPriceInfo.coinPriceInfo?.open24Hour
        }
    }

    private fun downloadImage(imgUrl: String?) {
        activity?.applicationContext?.let { it1 ->
            if (imgUrl != null) {
                GlideClient.downloadImage(it1, imgUrl, detailIcon)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun checkPercentageChangesAndSetArrow(view: View, coin: Coin) {
        val change24Hour = coin.displayCoinPriceInfo.coinPriceInfo?.change24Hour
        if (change24Hour != null) {
            if (change24Hour.contains('-')) {
                detailArrow.setImageDrawable(
                    resources.getDrawable(R.drawable.ic_arrow_down, view.context.theme)
                )
                changeTextColor(view.context, detailPctChange24, R.color.colorErrorRed)
                changeInfographic(view.context, detailInfographic1, R.drawable.ic_downwards)
            } else {
                detailArrow.setImageDrawable(
                    resources.getDrawable(R.drawable.ic_arrow_up, view.context.theme)
                )
                changeTextColor(view.context, detailPctChange24, R.color.colorAccent)
                changeInfographic(view.context, detailInfographic1, R.drawable.ic_upwards)
            }
        }
    }

    private fun changeTextColor(context: Context, textView: TextView, color: Int) {
        textView.setTextColor(resources.getColor(color, context.theme))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeInfographic(context: Context, imageView: ImageView, img: Int) {
        imageView.setImageDrawable(resources.getDrawable(img, context.theme))
    }
}
