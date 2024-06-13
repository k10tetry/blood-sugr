package com.k10tetry.bloodsugr.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.k10tetry.bloodsugr.common.milliToDate
import com.k10tetry.bloodsugr.common.milliToDay
import com.k10tetry.bloodsugr.common.milliToTime
import com.k10tetry.bloodsugr.common.round
import com.k10tetry.bloodsugr.databinding.ListItemMeasurementsBinding
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseModel
import javax.inject.Inject

class MainAdapter @Inject constructor() :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var mList = mutableListOf<BloodGlucoseModel>()

    fun insertItem(model: BloodGlucoseModel) {
        mList.add(0, model)
        notifyItemInserted(0)
    }

    fun submitList(list: List<BloodGlucoseModel>) {
        mList = list.sortedBy { -it.milliseconds }.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            ListItemMeasurementsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(mainViewHolder: MainViewHolder, position: Int) {
        mainViewHolder.onBind(mList[position])
    }

    inner class MainViewHolder(private val binding: ListItemMeasurementsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: BloodGlucoseModel) {
            binding.textViewMeasurement.text = model.value.round().toString()
            binding.textViewUnit.text = model.units.units
            binding.textViewDate.text = model.milliseconds.milliToDate()
            binding.textViewTime.text = "${model.milliseconds.milliToDay()}, ${model.milliseconds.milliToTime()}"
        }

    }

}