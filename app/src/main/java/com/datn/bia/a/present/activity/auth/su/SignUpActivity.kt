package com.datn.bia.a.present.activity.auth.su

import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_sign_up
}