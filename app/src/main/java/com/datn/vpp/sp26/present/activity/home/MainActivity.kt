package com.datn.vpp.sp26.present.activity.home

import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.selectedTab
import com.datn.vpp.sp26.common.base.ext.unSelectedTab
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityMainBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {

    private var vpgAdapter: VpgAdapter? = null

    private val onPageChangeCallBack = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            when (position) {
                0 -> {
                    binding.tvTabHome.selectedTab(this@MainActivity)
                    binding.tvTabCart.unSelectedTab(this@MainActivity)
                    binding.tvTabProfile.unSelectedTab(this@MainActivity)

                    binding.icHome.isActivated = true
                    binding.icCart.isActivated = false
                    binding.icProfile.isActivated = false
                }

                1 -> {
                    binding.tvTabHome.unSelectedTab(this@MainActivity)
                    binding.tvTabCart.selectedTab(this@MainActivity)
                    binding.tvTabProfile.unSelectedTab(this@MainActivity)

                    binding.icHome.isActivated = false
                    binding.icCart.isActivated = true
                    binding.icProfile.isActivated = false
                }

                2 -> {
                    binding.tvTabHome.unSelectedTab(this@MainActivity)
                    binding.tvTabCart.unSelectedTab(this@MainActivity)
                    binding.tvTabProfile.selectedTab(this@MainActivity)

                    binding.icHome.isActivated = false
                    binding.icCart.isActivated = false
                    binding.icProfile.isActivated = true
                }
            }
        }
    }

    override fun getLayoutActivity(): Int = R.layout.activity_main

    override fun initViews() {
        super.initViews()

        if (SharedPrefCommon.isFirstInstall) SharedPrefCommon.isFirstInstall = false
        initVpg()

        Log.d("duylt", "${
            Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)
        }")

        Log.d("duylt", "${
            Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.user?.id
        }")

        Log.d("duylt", "Token; ${SharedPrefCommon.token}")
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnHome.click {
            if (binding.vpgMain.currentItem != 0) binding.vpgMain.currentItem = 0
        }

        binding.btnCart.click {
            if (binding.vpgMain.currentItem != 1) binding.vpgMain.currentItem = 1
        }

        binding.btnProfile.click {
            if (binding.vpgMain.currentItem != 2) binding.vpgMain.currentItem = 2
        }
    }

    override fun onDestroy() {
        vpgAdapter = null
        binding.vpgMain.unregisterOnPageChangeCallback(onPageChangeCallBack)

        super.onDestroy()
    }

    private fun initVpg() = binding.vpgMain.apply {
        vpgAdapter = VpgAdapter(this@MainActivity)

        adapter = vpgAdapter
        registerOnPageChangeCallback(onPageChangeCallBack)
        isUserInputEnabled = true
    }
}