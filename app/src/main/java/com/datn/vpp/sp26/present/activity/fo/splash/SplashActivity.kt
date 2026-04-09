package com.datn.vpp.sp26.present.activity.fo.splash

import android.annotation.SuppressLint
import android.content.Intent
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.application.GlobalApp
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivitySplashBinding
import com.datn.vpp.sp26.present.activity.fo.language.LanguageActivity
import com.datn.vpp.sp26.present.activity.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_splash

    override fun initViews() {
        super.initViews()

        GlobalApp.jsonSearchResult = ""

        if (SharedPrefCommon.isFirstInstall) {
            startActivity(Intent(this, LanguageActivity::class.java).apply {
                putExtra(AppConst.KEY_FROM_SPLASH, true)
            })
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}