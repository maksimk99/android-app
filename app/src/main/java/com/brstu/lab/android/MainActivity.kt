package com.brstu.lab.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = MainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    fun onMovieInfoClick(movie: CarSale) {
        val fragment = InfoFragment()
        val arguments = Bundle()
        arguments.putString(InfoFragment.IMAGE_URL_KEY, movie.imageUrl)
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, null)
            .addToBackStack("InfoFragment")
            .commit()
    }
}
