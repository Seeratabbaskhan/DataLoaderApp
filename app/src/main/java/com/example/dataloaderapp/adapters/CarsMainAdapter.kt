package com.example.dataloaderapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dataloaderapp.R
import com.example.dataloaderapp.extensions.formatTime
import com.example.dataloaderapp.extensions.isRTL
import com.example.dataloaderapp.extensions.showToast
import com.example.dataloaderapp.roomdb.CarsTable

class CarsMainAdapter(
    private val appCompatActivity: AppCompatActivity,
    private val onItemClick: (Int, Int) -> Unit
) : RecyclerView.Adapter<CarsMainAdapter.CarViewHolder>(), Filterable {

    private var carsList = mutableListOf<CarsTable>()
    private var exampleListFull = mutableListOf<CarsTable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_car, parent, false
            )
        )
    }

    override fun getItemCount(): Int = carsList.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bindData(carsList[position])
    }

    fun addData(newList: List<CarsTable>?) {

        newList?.let {
            carsList.clear()
            carsList.addAll(it)
            exampleListFull.addAll(it)
            notifyDataSetChanged()
        }
    }


    override fun getFilter(): Filter {
        return filter
    }


    private val filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList = mutableListOf<CarsTable>()

            if (charSequence.isEmpty()) {
                filteredList.addAll(exampleListFull)
            } else {
                val filterPattern = charSequence.toString().lowercase().trim()

                for (item in exampleListFull) {
                    if (item.titleEn.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            try {
                carsList = (filterResults.values as List<CarsTable>).toMutableList()

                notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle exceptions if needed
            }
        }
    }

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val favImage = itemView.findViewById<ImageView>(R.id.iv_fvrt_id)
        private val img_car = itemView.findViewById<ImageView>(R.id.img_car)
        private val carBrandName = itemView.findViewById<TextView>(R.id.tv_car_brand_id)
        private val carAmount = itemView.findViewById<TextView>(R.id.tv_amount_id)
        private val endingTime = itemView.findViewById<TextView>(R.id.tv_ending_time)

        fun bindData(model: CarsTable) {

            val imageUrl = "https://placehold.co/150x130/png?text=${model.carID}"
            Glide.with(appCompatActivity).load(imageUrl).into(img_car)
            endingTime.text = model.endTimeInSec.formatTime()

            favImage.setImageResource(if (model.favourite == 1) R.drawable.icon_heart_hollow else R.drawable.icon_heart_filled)


            if (itemView.isRTL()) {
                carBrandName.text = model.titleAr
                carAmount.text = "${model.currencyAr} ${model.currentPrice}"
            } else {
                carBrandName.text = model.titleEn
                carAmount.text = "${model.currencyEn} ${model.currentPrice}"
            }


            favImage.setOnClickListener {
                val favourite = model.favourite as? Int
                if (favourite != 1) {
                    favImage.setImageResource(R.drawable.icon_heart_hollow)
                    appCompatActivity.showToast(
                        appCompatActivity.getString(R.string.removed)
                    )
                } else {
                    favImage.setImageResource(R.drawable.icon_heart_filled)
                    appCompatActivity.showToast(
                        appCompatActivity.getString(R.string.added)
                    )
                }
                onItemClick(model.carID, if (model.favourite == 0) 1 else 0)
            }

        }

    }


}