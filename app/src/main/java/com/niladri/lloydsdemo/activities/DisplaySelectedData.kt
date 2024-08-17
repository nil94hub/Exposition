package com.niladri.lloydsdemo.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.niladri.lloydsdemo.R
import com.niladri.lloydsdemo.databinding.ActivityShowselecteddataBinding
import com.niladri.lloydsdemo.viewmodels.DisplaySelectedViewModel
import com.niladri.lloydsdemo.utils.Constants.POSITION
import com.niladri.lloydsdemo.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

internal const val LOADING = "Loading item number "

@AndroidEntryPoint
class DisplaySelectedData : AppCompatActivity() {
    private lateinit var binding: ActivityShowselecteddataBinding
    private val viewModel: DisplaySelectedViewModel by viewModels()
    private var index: Int = 0

    @Inject
    lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowselecteddataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        index = intent.getIntExtra(POSITION, 0)
        viewModel.getData(index)
        viewModel.genericDialogEvent.observe(this) {
            utils.showAlertDialog(resources.getString(R.string.generic_alert), this)
        }
        showSelectedData()
    }


    private fun showSelectedData() {
        viewModel.getSelectedData.observe(this) {
            it?.let { utils.toast(this, LOADING + it.id) }
            binding.tvId.setText(Integer.toString(it.id) + " ")
            binding.tvBody.text = it.body
            viewModel.removeObservers()
        }
    }
}
