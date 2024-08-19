package com.niladri.lloydsdemo.utils
/**
 *  A generic class that holds a value with its loading status
 * */
data class ResultWrapper<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val title: String?
) {
    companion object {
        fun <T> success(data: T?): ResultWrapper<T> {
            return ResultWrapper(Status.SUCCESS, data, null, null)
        }

        fun <T> error(msg: String, data: T? = null, title: String? = null): ResultWrapper<T> {
            return ResultWrapper(Status.ERROR, data, msg, title)
        }

        fun <T> loading(data: T? = null): ResultWrapper<T> {
            return ResultWrapper(Status.LOADING, data, null, null)
        }
    }
}