package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseActivity
import ru.job4j.cryptocompareapp.presentation.view.fragment.DetailCoinFragment
import ru.job4j.cryptocompareapp.presentation.view.fragment.NewsArticlesFragment
import ru.job4j.cryptocompareapp.presentation.view.fragment.TopCoinsFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.AppViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.Coin
import javax.inject.Inject

class MainActivity :
    BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    TopCoinsFragment.CallbackToDetail {
    private var isBottomNavViewVisible = true
    var appViewModel: AppViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbarAndBottomNavigationView()
        if (savedInstanceState == null) {
            loadTopCoinsFragment()
        }
    }

    private fun setToolbarAndBottomNavigationView() {
        val mainToolbar = toolbar
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setBottomNavigationViewVisible()
        bottomNavView.inflateMenu(R.menu.bottom_navigation_menu)
        bottomNavView.setOnNavigationItemSelectedListener(this)
    }

    private fun loadTopCoinsFragment() {
        setBottomNavigationViewVisible()
        loadFragment(TopCoinsFragment(), TOP_COINS_FRAGMENT)
    }

    private fun loadDetailCoinInfoFragment() {
        setBottomNavigationViewGone()
        loadFragment(DetailCoinFragment(), DETAIL_COIN_FRAGMENT)
    }

    private fun loadNewsArticlesFragment() {
        setBottomNavigationViewVisible()
        loadFragment(NewsArticlesFragment(), NEWS_ARTICLES_FRAGMENT)
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
            R.id.bottomNavMenuTopCoinsId -> {
                loadTopCoinsFragment()
                true
            }
            R.id.bottomNavMenuSpyId -> true
            R.id.bottomNavMenuNewsId -> {
                loadNewsArticlesFragment()
                true
            }
            else -> false
        }
    }

    private fun setBottomNavigationViewVisible() {
        isBottomNavViewVisible = true
        bottomNavView.visibility = View.VISIBLE
    }

    private fun setBottomNavigationViewGone() {
        isBottomNavViewVisible = false
        bottomNavView.visibility = View.GONE
    }

    override fun openCoinDetailClick(coin: Coin) {
        appViewModel?.setLiveDataSelectedCoin(coin)
        loadDetailCoinInfoFragment()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) finish() else super.onBackPressed()
        if (isBottomNavViewVisible) return else bottomNavView.visibility = View.VISIBLE
    }

    companion object {
        private const val TOP_COINS_FRAGMENT = "topCoinsFragment"
        private const val DETAIL_COIN_FRAGMENT = "detailCoinFragment"
        private const val NEWS_ARTICLES_FRAGMENT = "newsArticlesFragment"
    }
}
