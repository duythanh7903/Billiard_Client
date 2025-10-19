package com.datn.bia.a.present.activity.fo.language

import android.content.Intent
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivityLanguageBinding
import com.datn.bia.a.model.domain.Language
import com.datn.bia.a.present.activity.fo.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageActivity: BaseActivity<ActivityLanguageBinding>() {
    private lateinit var languageAdapter: LanguageAdapter

    override fun getLayoutActivity(): Int = R.layout.activity_language

    override fun initViews() {
        super.initViews()

        initRcvLanguage()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icTick.click {
            val isoLanguageCurrent = languageAdapter.getIsoLanguageCurrent()
            SharedPrefCommon.languageCode = isoLanguageCurrent

            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }

    private fun initRcvLanguage() = binding.rcvLanguage.apply {
        languageAdapter = LanguageAdapter(this@LanguageActivity) { index, language ->
            languageAdapter.indexSelect = index
        }.apply {
            submitData(Language.getListLanguageApp())
        }

        adapter = languageAdapter
    }
}