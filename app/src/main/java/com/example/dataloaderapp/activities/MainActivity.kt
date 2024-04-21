package com.example.dataloaderapp.activities

import com.example.dataloaderapp.databinding.ActivityMainBinding
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dataloaderapp.R
import com.example.dataloaderapp.adapters.CarsMainAdapter
import com.example.dataloaderapp.extensions.MainModel
import com.example.dataloaderapp.extensions.isRTL
import com.example.dataloaderapp.extensions.showToast
import com.example.dataloaderapp.roomdb.CarsTable
import com.example.dataloaderapp.viewmodels.CarViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityMainBinding
    private val carViewModel: CarViewModel by viewModels()
    private var backPressCounter = 0

    private var carsMainAdapter: CarsMainAdapter? = null

    private var callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                backPressCounter++
                if (backPressCounter > 1) {
                    finish()
                }

                if (backPressCounter == 1) {
                    showToast("Press again to exit!")
                }
                object : CountDownTimer(2000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        backPressCounter = 0
                    }

                }.start()

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // handle callback
        onBackPressedDispatcher.addCallback(this, callback)

        setupClickListner()
        setupRecycleAdapter()
        fetchObserver() // observe for data
        setupEditTextListener()

    }

    private fun setupClickListner() {
        binding.ivBackPressId.setOnClickListener(this)
        binding.ivMenuId.setOnClickListener(this)
    }

    private fun setupEditTextListener() {
        binding.etSearchId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                carsMainAdapter?.getFilter()?.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    private fun setupRecycleAdapter() {
        carsMainAdapter = CarsMainAdapter(this@MainActivity)
        binding.rvCarListId.adapter = carsMainAdapter
    }

    private fun fetchObserver() {
        carViewModel.allCars.observe(this) {
            if (it.isNullOrEmpty()) {
                binding.progressId.visibility = View.VISIBLE
                saveDataLocally()
            } else {
                binding.progressId.visibility = View.GONE
                carsMainAdapter?.addData(
                    it
                )
            }
        }
    }


    private fun saveDataLocally() {
        try {
            val json = assets.open("cars.json").bufferedReader().use { it.readText() }
            val mainModel = Gson().fromJson(json, MainModel::class.java)

            mainModel.cars?.let {
                val carEntityList = it.map { car ->
                    CarsTable(
                        car.carID,
                        car.image,
                        car.titleEn,
                        car.titleAr,
                        car.auctionInfo.bids ?: 0,
                        car.auctionInfo.endTimeInSec ?: 0,
                        car.auctionInfo.currencyEn ?: "",
                        car.auctionInfo.currencyAr ?: "",
                        car.auctionInfo.currentPrice ?: 0,
                        car.auctionInfo.priority ?: 0
                    )
                }

                carViewModel.insertCarsIfNotExists(carEntityList)

            }

        } catch (_: IOException) {
            binding.progressId.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        v?.id.let {
            if (it == R.id.iv_back_press_id) {
                callback.handleOnBackPressed()
            }else if (it == R.id.iv_menu_id) {
                onChangeLayoutDirectionButtonClick()
            }
        }
    }

    private fun onChangeLayoutDirectionButtonClick() {
        val currentLayoutDirection = binding.rvCarListId.layoutDirection
        Log.d("LayoutDirection", "Current Layout Direction: $currentLayoutDirection")

        val newLayoutDirection = if (binding.rvCarListId.isRTL()) {
            View.LAYOUT_DIRECTION_LTR
        } else {
            View.LAYOUT_DIRECTION_RTL
        }
        setLayoutDirection(newLayoutDirection)

        val updatedLayoutDirection = binding.rvCarListId.layoutDirection
        Log.d("LayoutDirection", "Updated Layout Direction: $updatedLayoutDirection")

        // Notify adapter about the layout direction change
        carsMainAdapter?.notifyDataSetChanged()
    }

    private fun setLayoutDirection(layoutDirection: Int) {
        binding.rvCarListId.layoutDirection = layoutDirection

    }


}