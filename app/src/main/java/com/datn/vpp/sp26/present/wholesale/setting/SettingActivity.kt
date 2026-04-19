package com.datn.vpp.sp26.present.wholesale.setting

import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.databinding.ActivitySettingWholesaleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingWholesaleBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_setting_wholesale
}