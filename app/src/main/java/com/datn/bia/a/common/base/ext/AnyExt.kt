package com.datn.bia.a.common.base.ext

import com.google.gson.Gson

fun Any.toJson(): String = Gson().toJson(this)