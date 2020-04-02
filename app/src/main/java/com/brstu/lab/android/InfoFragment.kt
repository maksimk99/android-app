package com.brstu.lab.android

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class InfoFragment : Fragment() {

    private var imageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString(IMAGE_URL_KEY) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch {
            val stream = URL(imageUrl).openStream()
            val bitmap = BitmapFactory.decodeStream(stream)
            withContext(Dispatchers.Main) {
                imageViewForInfo.setImageBitmap(bitmap)
            }
        }
    }

    companion object {
        const val IMAGE_URL_KEY = "image url key"
    }
}