package com.datn.bia.a.present.activity.auth.si

import android.content.Intent
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.ActivitySignInBinding
import com.datn.bia.a.present.activity.auth.su.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity: BaseActivity<ActivitySignInBinding>() {
    override fun getLayoutActivity(): Int = R.layout.activity_sign_in

    override fun onClickViews() {
        super.onClickViews()

        binding.tvSignUp.click {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}