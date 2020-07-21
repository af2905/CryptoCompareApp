package ru.job4j.cryptocompareapp.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.presentation.view.fragment.TopCoinsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    companion object {
    private const val TOP_COINS_FRAGMENT = "topCoinsFragment"
    }
}