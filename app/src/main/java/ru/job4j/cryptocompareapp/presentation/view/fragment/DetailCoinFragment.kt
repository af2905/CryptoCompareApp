package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_detail_coin_info.*
import kotlinx.android.synthetic.main.fragment_detail_coin_info.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.AppViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import ru.job4j.cryptocompareapp.repository.server.GlideClient
import javax.inject.Inject

class DetailCoinFragment : BaseFragment(), View.OnClickListener {
    private lateinit var coin: Coin
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
    private lateinit var cardPoweredBy: CardView
    private lateinit var cardCoinName: CardView
    private lateinit var cardCurrentPrice: CardView
    private lateinit var cardPriceChange24H: CardView
    private lateinit var cardMarketCap: CardView
    private lateinit var cardOpen24H: CardView
    private lateinit var cardHighLow24H: CardView
    private lateinit var cardTotalVol24H: CardView
    var appViewModel: AppViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_coin_info, container, false)
        initViews(view)
        initListeners()
        appViewModel?.getLiveDataSelectedCoin()?.observe(
            viewLifecycleOwner,
            Observer {
                val imgUrl = it.displayCoinPriceInfo.coinPriceInfo?.getFullImageUrl()
                downloadImage(imgUrl)
                setDataToViews(it)
                checkPercentageChangesAndSetArrow(view, it)
                coin = it
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
            cardPoweredBy = cardViewPoweredBy
            cardCoinName = cardViewCoinName
            cardCurrentPrice = cardViewCurrentPrice
            cardPriceChange24H = cardViewPriceChange24H
            cardMarketCap = cardViewMarketCap
            cardOpen24H = cardViewOpen24H
            cardHighLow24H = cardViewHighLow24H
            cardTotalVol24H = cardViewTotalVol24H
        }
    }

    private fun initListeners() {
        cardPoweredBy.setOnClickListener(this)
        cardCoinName.setOnClickListener(this)
        cardCurrentPrice.setOnClickListener(this)
        cardPriceChange24H.setOnClickListener(this)
        cardMarketCap.setOnClickListener(this)
        cardOpen24H.setOnClickListener(this)
        cardHighLow24H.setOnClickListener(this)
        cardTotalVol24H.setOnClickListener(this)
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
        val theme = view.context.theme
        if (change24Hour != null) {
            if (change24Hour.contains('-')) {
                detailArrow.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_down, theme))
            } else {
                detailArrow.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_up, theme))
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            cardViewPoweredBy -> {
                val address = Uri.parse(CRYPTOCOMPARE_LINK)
                val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
                startActivity(openLinkIntent)
            }
            cardViewCoinName -> showAlertDialog(coin)
            cardViewCurrentPrice -> showAlertDialog(coin)
            cardViewHighLow24H -> showAlertDialog(coin)
            cardViewMarketCap -> showAlertDialog(coin)
            cardViewOpen24H -> showAlertDialog(coin)
            cardViewPriceChange24H -> showAlertDialog(coin)
            cardViewTotalVol24H -> showAlertDialog(coin)
        }
    }

    private fun showAlertDialog(coin: Coin) {
        activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.app_name))
                .setMessage(setMessageToDialog(coin))
                .setPositiveButton(resources.getString(android.R.string.ok)) { dialog, _ ->
                    dialog.cancel()
                }.create().show()
        }
    }

    private fun setMessageToDialog(coin: Coin): String {
        return with(resources) {
            with(coin.coinBasicInfo) {
                with(coin.displayCoinPriceInfo.coinPriceInfo) {
                    String.format(
                        "\n%s: %s (%s)\n\n%s: %s\n\n%s: %s(%s%%)\n\n%s: %s\n\n%s: %s\n\n%s: %s\n\n%s: %s/%s\n",
                        getString(R.string.coin_name), fullName, name,
                        getString(R.string.price), this?.price,
                        getString(R.string.price_change), this?.change24Hour, this?.changePct24Hour,
                        getString(R.string.market_capitalization), this?.mktCap,
                        getString(R.string.total_volume_24h), this?.totalVolume24H,
                        getString(R.string.open_24h), this?.open24Hour,
                        getString(R.string.low_high_24h), this?.high24Hour, this?.low24Hour
                    )
                }
            }
        }
    }

    companion object {
        private const val CRYPTOCOMPARE_LINK = "https://min-api.cryptocompare.com"
    }
}