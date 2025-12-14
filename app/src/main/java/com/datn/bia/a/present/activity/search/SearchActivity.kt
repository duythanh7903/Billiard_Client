package com.datn.bia.a.present.activity.search

import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: BaseActivity<ActivitySearchBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_search
}