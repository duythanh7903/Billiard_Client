package com.datn.bia.a.common.base.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.observeFlow(flow: Flow<T>, action: (t: T) -> Unit) {
    launch {
        flow.collect { value -> action(value) }
    }
}
fun <T> CoroutineScope.observe(flow: Flow<T>, action: (t: T) -> Unit) {
    launch {
        flow.collect { value -> action(value) }
    }
}