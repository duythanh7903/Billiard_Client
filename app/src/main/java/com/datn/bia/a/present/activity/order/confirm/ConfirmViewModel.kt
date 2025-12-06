package com.datn.bia.a.present.activity.order.confirm

import com.datn.bia.a.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(

): BaseViewModel() {
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()
}