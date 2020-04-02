package com.brstu.lab.android

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ListAdapter(
    val context: Context,
    var listOfCarSales: List<CarSale>,
    val onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<ListAdapter.ViewCell>() {

    interface OnItemClickListener {
        fun onItemClick(carSale: CarSale)
    }

    class ViewCell(cellView: View): RecyclerView.ViewHolder(cellView) {
        val titleText: TextView = cellView.findViewById(R.id.titleText)
        val titleYear: TextView = cellView.findViewById(R.id.yearText)
        val titleMileage: TextView = cellView.findViewById(R.id.mileageText)
        val titleEngineCapacity: TextView = cellView.findViewById(R.id.engineCapacityText)
        val titlePrice: TextView = cellView.findViewById(R.id.priceText)
        val poster: ImageView = cellView.findViewById(R.id.imageViewPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCell {
        val view = LayoutInflater.from(context).inflate(R.layout.item_advertisement, parent, false)
        return ViewCell(view)
    }

    override fun getItemCount(): Int {
        return listOfCarSales.size
    }

    override fun onBindViewHolder(holder: ViewCell, position: Int) {
        val carSale = listOfCarSales[position]
        holder.titleText.text = carSale.name
        holder.titleYear.text = carSale.year
        holder.titleMileage.text = carSale.mileage
        holder.titleEngineCapacity.text = carSale.engineCapacity
        holder.titlePrice.text = carSale.price

        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(carSale) }

        GlobalScope.launch {
            val stream = URL(carSale.imageUrl).openStream()
            val bitmap = BitmapFactory.decodeStream(stream)
            withContext(Dispatchers.Main) {
                holder.poster.setImageBitmap(bitmap)
            }
        }
    }
}