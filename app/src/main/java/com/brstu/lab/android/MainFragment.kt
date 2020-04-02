package com.brstu.lab.android

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.URL

class MainFragment : Fragment(), ListAdapter.OnItemClickListener {

    var bitmap: Bitmap? = null
    var listAdapter: ListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar.visibility = View.GONE

        val omdbApi = (activity?.application as TestApplication).searchService

        val applicationContext = activity?.applicationContext!!
        val db = Room.databaseBuilder(applicationContext, CarSaleDatabase::class.java, "search_movie.db")
            .fallbackToDestructiveMigration()
            .build()

        val movieDao = db.getMovieDao()

        buttonLoad.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            GlobalScope.launch {
                val result = doWork(omdbApi, movieDao)
                val listOfMovies = result.listOfCarSales
                if (listOfMovies != null) {
                    listAdapter?.listOfCarSales = listOfMovies
                    movieDao.addCarSales(listOfMovies)
                    withContext(Dispatchers.Main) {
                        listAdapter?.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
        listAdapter = ListAdapter(applicationContext, emptyList(), this)
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext,
            LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClick(carSale: CarSale) {
        (activity as? MainActivity)?.onMovieInfoClick(carSale)
    }

    suspend fun doWork(omdbApi: SearchApi?, movieDao: CarSaleDao): SearchResult = coroutineScope {
        async {
            var result: SearchResult
            try {
                result = omdbApi?.getCarSaleList() ?: SearchResult(movieDao.getAllCarSales())
            } catch (e: ConnectException) {
                result = SearchResult(movieDao.getAllCarSales())
            }
            return@async result
        }.await()
    }
}