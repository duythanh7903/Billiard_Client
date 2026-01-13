package com.datn.bia.a.present.activity.fo.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.datn.bia.a.R
import com.datn.bia.a.application.GlobalApp
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivitySplashBinding
import com.datn.bia.a.present.activity.fo.language.LanguageActivity
import com.datn.bia.a.present.activity.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_splash

    override fun initViews() {
        super.initViews()

        GlobalApp.jsonSearchResult = ""

        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPrefCommon.isFirstInstall) {
                startActivity(Intent(this, LanguageActivity::class.java).apply {
                    putExtra(AppConst.KEY_FROM_SPLASH, true)
                })
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 5000)
    }
}