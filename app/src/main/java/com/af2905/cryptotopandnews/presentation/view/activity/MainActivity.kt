package com.af2905.cryptotopandnews.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.af2905.cryptotopandnews.R
import com.af2905.cryptotopandnews.di.component.ViewModelComponent
import com.af2905.cryptotopandnews.presentation.base.BaseActivity
import com.af2905.cryptotopandnews.presentation.view.fragment.DetailCoinFragment
import com.af2905.cryptotopandnews.presentation.view.fragment.NewsArticlesFragment
import com.af2905.cryptotopandnews.presentation.view.fragment.TopCoinsFragment
import com.af2905.cryptotopandnews.presentation.viewmodel.AppViewModel
import com.af2905.cryptotopandnews.repository.database.entity.Coin
import com.af2905.cryptotopandnews.repository.database.entity.News
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity :
    BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    TopCoinsFragment.CallbackToCoinDetail,
    NewsArticlesFragment.CallbackToNewsDetail {
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
        supportActionBar?.setDisplayShowTitleEnabled(true)
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
            .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
            .replace(R.id.content, fragment, tag)
            .addToBackStack(tag)
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
                setTopCoinsItemEnabledAndChecked(isEnabled = false, isChecked = true)
                setNewsItemEnabledAndChecked(isEnabled = true, isChecked = false)
                loadTopCoinsFragment()
                true
            }
            R.id.bottomNavMenuNewsId -> {
                setNewsItemEnabledAndChecked(isEnabled = false, isChecked = true)
                setTopCoinsItemEnabledAndChecked(isEnabled = true, isChecked = false)
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

    override fun openNewsDetailClick(news: News) {
        val address = Uri.parse(news.url)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
        startActivity(openLinkIntent)
    }

    override fun onBackPressed() {
        setBottomNavViewBehaviorWhenOnBackPressed()
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) finish() else super.onBackPressed()
        if (isBottomNavViewVisible) return else bottomNavView.visibility = View.VISIBLE
    }

    private fun setTopCoinsItemEnabledAndChecked(isEnabled: Boolean, isChecked: Boolean) {
        bottomNavView.menu.findItem(R.id.bottomNavMenuTopCoinsId).isEnabled = isEnabled
        bottomNavView.menu.findItem(R.id.bottomNavMenuTopCoinsId).isChecked = isChecked
    }

    private fun setNewsItemEnabledAndChecked(isEnabled: Boolean, isChecked: Boolean) {
        bottomNavView.menu.findItem(R.id.bottomNavMenuNewsId).isEnabled = isEnabled
        bottomNavView.menu.findItem(R.id.bottomNavMenuNewsId).isChecked = isChecked
    }

    private fun setBottomNavViewBehaviorWhenOnBackPressed() {
        val fragments = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null) {
                if (fragment is TopCoinsFragment || fragment is DetailCoinFragment) {
                    setNewsItemEnabledAndChecked(isEnabled = true, isChecked = false)
                    setTopCoinsItemEnabledAndChecked(isEnabled = false, isChecked = true)
                }
                if (fragment is NewsArticlesFragment) {
                    setTopCoinsItemEnabledAndChecked(isEnabled = true, isChecked = false)
                    setNewsItemEnabledAndChecked(isEnabled = false, isChecked = true)
                }
            }
        }
    }

    companion object {
        private const val TOP_COINS_FRAGMENT = "topCoinsFragment"
        private const val DETAIL_COIN_FRAGMENT = "detailCoinFragment"
        private const val NEWS_ARTICLES_FRAGMENT = "newsArticlesFragment"

        @JvmStatic
        fun newInstance(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }
}
