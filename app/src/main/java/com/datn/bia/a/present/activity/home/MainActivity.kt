package com.datn.bia.a.present.activity.home

import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_main

    override fun initViews() {
        super.initViews()

        if (SharedPrefCommon.isFirstInstall) SharedPrefCommon.isFirstInstall = false
    }
}