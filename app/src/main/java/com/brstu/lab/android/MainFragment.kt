package com.brstu.lab.android

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*

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
                val result = doWork(omdbApi!!, movieDao)
                val listOfMovies = result.listOfCarSales
                if (listOfMovies != null) {
                    if (listOfMovies.isNotEmpty()) {
                        listAdapter?.listOfCarSales = listOfMovies
                        movieDao.addCarSales(listOfMovies)
                        withContext(Dispatchers.Main) {
                            errorMessage.visibility = View.INVISIBLE
                            progressBar.visibility = View.GONE
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            progressBar.visibility = View.GONE
                            errorMessage.visibility = View.VISIBLE
                        }
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

    suspend fun doWork(omdbApi: SearchApi, movieDao: CarSaleDao): SearchResult = coroutineScope  {
        async {
            return@async try {
                omdbApi.getCarSaleList()
            } catch (ex: Exception) {
                SearchResult(movieDao.getAllCarSales())
            }
        }.await()
    }
}