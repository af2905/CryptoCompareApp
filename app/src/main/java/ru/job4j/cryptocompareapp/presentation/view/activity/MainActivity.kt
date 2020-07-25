package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseActivity
import ru.job4j.cryptocompareapp.presentation.view.fragment.DetailCoinInfoFragment
import ru.job4j.cryptocompareapp.presentation.view.fragment.TopCoinsFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.CoinViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import javax.inject.Inject

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    TopCoinsFragment.CallbackToDetail {
    var coinViewModel: CoinViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainToolbar = toolbar
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            loadTopCoinsFragment()
        }
    }

    private fun loadTopCoinsFragment() {
        loadFragment(TopCoinsFragment(), TOP_COINS_FRAGMENT)
    }

    private fun loadDetailCoinInfoFragment() {
        loadFragment(DetailCoinInfoFragment(), DETAIL_COIN_INFO_FRAGMENT)
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.bottomNavMenuTopCoinsId -> true
            R.id.bottomNavMenuSpyId -> true
            R.id.bottomNavMenuNewsId -> true
            else -> false
        }
    }

    companion object {
        private const val TOP_COINS_FRAGMENT = "topCoinsFragment"
        private const val DETAIL_COIN_INFO_FRAGMENT = "detailCoinInfoFragment"
    }

    override fun openCoinDetailClick(coin: Coin) {
        coinViewModel?.setLiveDataSelectedCoin(coin)
        loadDetailCoinInfoFragment()
    }
}