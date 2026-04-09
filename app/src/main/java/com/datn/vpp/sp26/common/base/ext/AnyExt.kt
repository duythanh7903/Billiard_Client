package com.datn.vpp.sp26.common.base.ext

import com.google.gson.Gson

fun Any.toJson(): String = Gson().toJson(this)