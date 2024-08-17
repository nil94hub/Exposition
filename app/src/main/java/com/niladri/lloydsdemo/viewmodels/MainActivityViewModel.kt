package com.niladri.lloydsdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niladri.lloydsdemo.model.PostResponse
import com.niladri.lloydsdemo.repository.Repo
import com.niladri.lloydsdemo.utils.ResultWrapper
import com.niladri.lloydsdemo.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val userRepository: Repo) :
    ViewModel() {
    // Observable loading state
    private val _isLoadingState = MutableLiveData(false)
    val isLoadingState: LiveData<Boolean> = _isLoadingState

    private val _errorDialogEvent = MutableLiveData<Boolean>()
    val genericDialogEvent: LiveData<Boolean> = _errorDialogEvent

    private val _getPostData = MutableLiveData<PostResponse>()
    val getPostData: LiveData<PostResponse> = _getPostData

    fun makeApiCall() {
        viewModelScope.launch { userRepository.getPosts() }
    }

    fun getData() {
        removeObservers()
        userRepository.postLiveData.observeForever(postDataObserver)
    }

    fun removeObservers() {
        userRepository.postLiveData.removeObserver(postDataObserver)
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
                _getPostData.postValue(result.data)
            }
        }
    }
}
