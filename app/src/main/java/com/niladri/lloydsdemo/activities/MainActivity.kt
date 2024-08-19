package com.niladri.lloydsdemo.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.niladri.lloydsdemo.LloydsApplication
import com.niladri.lloydsdemo.R
import com.niladri.lloydsdemo.adapter.PostsAdapter
import com.niladri.lloydsdemo.databinding.ActivityMainBinding
import com.niladri.lloydsdemo.callback.OnClick
import com.niladri.lloydsdemo.network.CheckInternet.Companion.isNetworkAvailable
import com.niladri.lloydsdemo.utils.Constants.POSITION
import com.niladri.lloydsdemo.utils.ProgressDialogue.Companion.progressDialog
import com.niladri.lloydsdemo.utils.Utils
import com.niladri.lloydsdemo.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnClick {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var postAdapter: PostsAdapter
    private lateinit var lloydsApplication: LloydsApplication
    private lateinit var progress: Dialog

    @Inject
    lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progress = progressDialog(this)
        if (isNetworkAvailable(this))
            viewModel.makeApiCall()
        else utils.showAlertDialog(
            resources.getString(R.string.generic_alert),
            this
        )
        viewModel.getPostData.observe(this) {
            it?.let { postAdapter.apply { setPostData(it, this@MainActivity) } }
        }
        viewModel.genericDialogEvent.observe(this) {
            if (it)
                utils.showAlertDialog(resources.getString(R.string.generic_alert), this)
        }
        viewModel.isLoadingState.observe(this) {
            when (it) {
                true -> progress.show()

                false -> progress.dismiss()
            }
        }
        prepareRecyclerView()
        lloydsApplication = LloydsApplication()
        lloydsApplication.setToastEnabled(true, this)
    }

    private fun prepareRecyclerView() {
        postAdapter = PostsAdapter(this)
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = postAdapter
        }
    }


    override fun passData(index: Int) {
        val intent = Intent(this, DisplaySelectedData::class.java)
        intent.putExtra(POSITION, index)
        startActivity(intent)
    }
}