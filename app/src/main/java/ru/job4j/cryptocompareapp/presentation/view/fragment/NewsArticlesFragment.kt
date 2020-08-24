package ru.job4j.cryptocompareapp.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news_articles.view.*
import ru.job4j.cryptocompareapp.R
import ru.job4j.cryptocompareapp.di.component.ViewModelComponent
import ru.job4j.cryptocompareapp.presentation.adapter.NewsAdapter
import ru.job4j.cryptocompareapp.presentation.base.BaseFragment
import ru.job4j.cryptocompareapp.presentation.decoration.DivItemDecoration
import ru.job4j.cryptocompareapp.presentation.item.IClickListener
import ru.job4j.cryptocompareapp.presentation.utils.NewsDiffUtilCallback
import ru.job4j.cryptocompareapp.presentation.viewmodel.AppViewModel
import ru.job4j.cryptocompareapp.repository.database.entity.News
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsArticlesFragment : BaseFragment() {
    private lateinit var recycler: RecyclerView
    private val newsAdapter = NewsAdapter()
    private var callbackToDetail: CallbackToNewsDetail? = null
    private val disposeBag = CompositeDisposable()
    private lateinit var swipeNewsArticlesRefreshLayout: SwipeRefreshLayout
    private val clickListener: IClickListener<News> = object : IClickListener<News> {
        override fun openDetailInfo(m: News) = openNewsDetailInfo(m)
    }
    var appViewModel: AppViewModel? = null
        @Inject set

    override fun injectDependency(component: ViewModelComponent) {
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_articles, container, false)
        swipeNewsArticlesRefreshLayout = view.swipeNewsArticlesRefreshLayout
        initRecyclerView(view, newsAdapter)
        loadDataFromViewModel()
        refreshLayoutWithDelay()
        return view
    }

    private fun initRecyclerView(view: View, newsAdapter: NewsAdapter) {
        recycler = view.recyclerViewNewsArticles
        newsAdapter.setClickListener(clickListener)
        recycler.adapter = newsAdapter
        recycler.addItemDecoration(DivItemDecoration(16, 8))
    }

    private fun loadDataFromViewModel() {
        appViewModel?.getLiveDataNewsArticlesList()?.observe(
            viewLifecycleOwner, Observer { setDataInAdapter(newsAdapter, it) })
    }

    private fun setDataInAdapter(newsAdapter: NewsAdapter, newsArticles: List<News>): Disposable {
        val listOfNews: Observable<List<News>> = Observable.fromArray(newsArticles)
        val disposable = listOfNews
            .map { DiffUtil.calculateDiff(NewsDiffUtilCallback(newsAdapter.getNewsArticles(), it)) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { newsAdapter.setNewsArticles(newsArticles) }
            .subscribe { it.dispatchUpdatesTo(newsAdapter) }
        disposeBag.add(disposable)
        return disposable
    }

    private fun refreshLayoutWithDelay() {
        swipeNewsArticlesRefreshLayout.setOnRefreshListener {
            disposeBag.add(Completable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    swipeNewsArticlesRefreshLayout.isRefreshing = false
                    val animId = R.anim.layout_animation_fall_down
                    recycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, animId)
                    Toast.makeText(context, R.string.just_updated, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    fun openNewsDetailInfo(news: News) {
        callbackToDetail?.openNewsDetailClick(news)
    }

    interface CallbackToNewsDetail {
        fun openNewsDetailClick(news: News)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.callbackToDetail = context as CallbackToNewsDetail
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        callbackToDetail = null
    }
}
