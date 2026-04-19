package com.datn.vpp.sp26.present.user.activity.fo.language

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityLanguageBinding
import com.datn.vpp.sp26.domain.model.domain.Language
import com.datn.vpp.sp26.present.user.activity.fo.splash.SplashActivity
import com.datn.vpp.sp26.present.user.activity.home.MainActivity
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {
    private var languageAdapter: LanguageAdapter? = null
    private var loadingDialog: LoadingDialog? = null

    private var isFromSplash: Boolean = false

    override fun getLayoutActivity(): Int = R.layout.activity_language

    override fun initViews() {
        super.initViews()

        isFromSplash = intent.getBooleanExtra(AppConst.KEY_FROM_SPLASH, false)
        loadingDialog = LoadingDialog(this)
        initRcvLanguage()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icTick.click {
            val isoLanguageCurrent = languageAdapter?.getIsoLanguageCurrent() ?: "en"
            SharedPrefCommon.languageCode = isoLanguageCurrent

            if (isFromSplash) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                loadingDialog?.show()

                Handler(Looper.getMainLooper()).postDelayed({
                    loadingDialog?.dismiss()
                    startActivity(Intent(this, SplashActivity::class.java))
                    finishAffinity()
                }, 3_000)
            }
        }
    }

    private fun initRcvLanguage() = binding.rcvLanguage.apply {
        languageAdapter = LanguageAdapter(this@LanguageActivity) { index, language ->
            languageAdapter?.indexSelect = index
        }.apply {
            submitData(Language.getListLanguageApp())
        }

        adapter = languageAdapter
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null
        languageAdapter?.list?.clear()
        languageAdapter = null

        super.onDestroy()
    }
}