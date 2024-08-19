package com.niladri.lloydsdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.niladri.lloydsdemo.model.PostResponse
import com.niladri.lloydsdemo.model.PostResponseItem
import com.niladri.lloydsdemo.repository.Repo
import com.niladri.lloydsdemo.utils.ResultWrapper
import com.niladri.lloydsdemo.utils.Status
import com.niladri.lloydsdemo.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class DisplaySelectedViewModel @Inject constructor(private val repository: Repo) : ViewModel() {
    @Inject
    lateinit var utils: Utils

    //isLoadingState can be used in future to show progress dialogue or some other actions
    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _errorDialogEvent = MutableLiveData<Boolean>()
    val genericDialogEvent: LiveData<Boolean> = _errorDialogEvent

    private val _getSelectedData = MutableLiveData<PostResponseItem>()
    val getSelectedData: LiveData<PostResponseItem> = _getSelectedData

    private var getIndex: Int = 0


    fun getData(index: Int) {
        getIndex = index
        repository.postLiveData.observeForever(postDataObserver)
    }

    override fun onCleared() {
        super.onCleared()
        repository.postLiveData.removeObserver(postDataObserver)
    }
    private val postDataObserver = Observer<ResultWrapper<PostResponse>> { result ->
        when (result.status) {
            Status.LOADING -> _isLoadingState.postValue(true)
            Status.ERROR -> {
                _isLoadingState.postValue(false)
                _errorDialogEvent.postValue(true)
            }
            Status.SUCCESS -> {
                _isLoadingState.postValue(false)
                result.data?.let { _getSelectedData.postValue(it[getIndex]) }
            }
        }
    }

}