package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_news_articles.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.viewmodel.AppViewModel
import javax.inject.Inject

class NewsArticlesFragment : BaseFragment() {
    var appViewModel: AppViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_articles, container, false)
        val newsTitle: TextView = view.newsTitle
        val newsSourceName: TextView = view.newsSourceName

        appViewModel?.getLiveDataNewsArticlesList()
            ?.observe(
                viewLifecycleOwner, Observer {
                    newsTitle.text = it[0].title
                    newsSourceName.text = it[0].newsSourceInfo?.name
                }
            )
        return view
    }
}