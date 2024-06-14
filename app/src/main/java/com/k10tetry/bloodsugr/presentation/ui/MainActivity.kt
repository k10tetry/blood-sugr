package com.k10tetry.bloodsugr.presentation.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.k10tetry.bloodsugr.R
import com.k10tetry.bloodsugr.common.ErrorType
import com.k10tetry.bloodsugr.common.round
import com.k10tetry.bloodsugr.databinding.ActivityMainBinding
import com.k10tetry.bloodsugr.domain.model.BloodGlucoseUnits
import com.k10tetry.bloodsugr.presentation.utils.ItemDecorator
import com.k10tetry.bloodsugr.presentation.utils.hideKeyboard
import com.k10tetry.bloodsugr.presentation.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: MainAdapter

    @Inject
    lateinit var itemDecorator: ItemDecorator

    @Inject
    lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    private var isAddOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        initObservers()

        viewModel.initialize()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { setMeasurementUnits() }
                launch { setAverageMeasurement() }
                launch { setConvertedInput() }
                launch { setMeasurementData() }
                launch { checkError() }
            }
        }
    }

    private suspend fun checkError() {
        viewModel.toastState.collect {
            val message = when (it) {
                ErrorType.NETWORK_ERROR -> getString(R.string.network_connection)
                ErrorType.PARSING_ERROR -> getString(R.string.parsing_error)
                ErrorType.OTHER -> getString(R.string.something_went_wrong)
            }
            binding.root.snackbar(message)
        }
    }

    private suspend fun setMeasurementData() {
        viewModel.measurementListState.collect {

            binding.textViewHistory.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE

            if (isAddOperation) {
                isAddOperation = false
                binding.editTextMeasurement.run {
                    clearFocus()
                    this.text = null
                }
                adapter.insertItem(it.last())
                binding.recycleView.scrollToPosition(0)
            } else {
                adapter.submitList(it)
            }
        }
    }

    private suspend fun setConvertedInput() {
        viewModel.inputState.collect {
            if (it.round() != 0.0) {
                binding.editTextMeasurement.setText(it.round().toString())
            }
        }
    }

    private suspend fun setAverageMeasurement() {
        viewModel.averageState.collect {
            binding.textViewMeasurement.text = it.round().toString()
        }
    }

    private suspend fun setMeasurementUnits() {
        viewModel.unitState.collect {
            val checkId = when (it) {
                BloodGlucoseUnits.MILLI_MOLES_LTR -> binding.radioMmol.id
                BloodGlucoseUnits.MILLI_GRAM_DL -> binding.radioMg.id
            }
            binding.radioGroup.check(checkId)
            binding.textViewUnitLabel.text = it.units
            binding.textViewUnits.text = it.units

            binding.editTextMeasurement.text?.toString()?.toDoubleOrNull()?.let { input ->
                viewModel.convertInputMeasurement(input, it)
            }

        }
    }

    private fun initViews() {
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = linearLayoutManager
        binding.recycleView.addItemDecoration(itemDecorator)
        binding.recycleView.setHasFixedSize(true)

        onClickRadioButtonMG()
        onClickRadioButtonMMOL()
        onClickSave()
    }

    private fun onClickRadioButtonMG() {
        binding.radioMg.setOnClickListener {
            viewModel.saveUnits(BloodGlucoseUnits.MILLI_GRAM_DL)
        }
    }

    private fun onClickRadioButtonMMOL() {
        binding.radioMmol.setOnClickListener {
            viewModel.saveUnits(BloodGlucoseUnits.MILLI_MOLES_LTR)
        }
    }

    private fun onClickSave() {
        binding.buttonSave.setOnClickListener {

            if (binding.editTextMeasurement.text?.isEmpty() == true) {
                return@setOnClickListener
            }

            if (isAddOperation.not()) {
                isAddOperation = true
            }

            binding.editTextMeasurement.text?.let {
                val measurement = it.toString().toDoubleOrNull() ?: 0.0
                val timeInMillis = Calendar.getInstance().timeInMillis
                viewModel.saveMeasurement(measurement, viewModel.unitState.value, timeInMillis)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            if (currentFocus is AppCompatEditText) {
                val rect = Rect().apply { currentFocus?.getGlobalVisibleRect(this) }
                if (!rect.contains(ev.x.toInt(), ev.y.toInt())) {
                    currentFocus?.clearFocus()
                    hideKeyboard(binding.root)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}