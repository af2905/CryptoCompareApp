package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.presentation.view.fragment.TopCoinsFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
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
    }
}