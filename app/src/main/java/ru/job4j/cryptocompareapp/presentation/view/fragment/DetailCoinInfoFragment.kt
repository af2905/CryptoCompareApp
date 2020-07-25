package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.item_coin_info.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import javax.inject.Inject

class DetailCoinInfoFragment : BaseFragment() {
    var coinViewModel: CoinViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_coin_info, container, false)
        val txtFullName = view.txtFullName

        coinViewModel?.getLiveDataSelectedCoin()?.observe(viewLifecycleOwner, Observer {
            val coinFullName = it.coinBasicInfo.fullName
            txtFullName.text = coinFullName
        })
        return view
    }
}