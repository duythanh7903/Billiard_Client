package com.datn.bia.a.present.activity.setting

import android.content.Intent
import android.util.Log
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.ActivitySettingBinding
import com.datn.bia.a.model.domain.SettingCat
import com.datn.bia.a.model.domain.SettingItem
import com.datn.bia.a.present.activity.setting.adapter.SettingCatAdapter
import com.datn.bia.a.present.activity.setting.adapter.SettingItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity: BaseActivity<ActivitySettingBinding>() {

    private var settingCatAdapter: SettingCatAdapter? = null

    override fun getLayoutActivity(): Int = R.layout.activity_setting

    override fun initViews() {
        super.initViews()

        initRcvSetting()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }

        binding.icChat.click {

        }
    }

    private fun initRcvSetting() = binding.rcvSettingCat.apply {
        settingCatAdapter = SettingCatAdapter(
            contextParams = this@SettingActivity,
            onSettingItem = { index, setting ->
                Log.d("duylt", "Index: $index, Setting: $setting")
            }
        ).apply {
            submitData(SettingCat.getAllSettingsCat())
        }

        adapter = settingCatAdapter
    }

}