package com.datn.bia.a.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {
    protected fun launchIO(
        onError: (Throwable) -> Unit =  {},
        onCompleted: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(mainDispatcher) {
        try {
            withContext(ioDispatcher) {
                block.invoke(this)
            }
        } catch (e: Exception) {
            onError.invoke(e)
        } finally {
            onCompleted?.invoke()
        }
    }
}