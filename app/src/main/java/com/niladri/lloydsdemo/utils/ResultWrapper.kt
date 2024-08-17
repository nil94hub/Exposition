package com.niladri.lloydsdemo.utils

data class ResultWrapper<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val title: String?
) {
    companion object {
        fun <T> Success(data: T?): ResultWrapper<T> {
            return ResultWrapper(Status.SUCCESS, data, null, null)
        }

        fun <T> Error(msg: String, data: T? = null, title: String? = null): ResultWrapper<T> {
            return ResultWrapper(Status.ERROR, data, msg, title)
        }

        fun <T> Loading(data: T? = null): ResultWrapper<T> {
            return ResultWrapper(Status.LOADING, data, null, null)
        }
    }
}