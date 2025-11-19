package com.datn.bia.a.present.fragment.profile

import android.content.Intent
import com.datn.bia.a.common.base.BaseFragment
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.FragmentProfileBinding
import com.datn.bia.a.present.activity.auth.si.SignInActivity
import com.datn.bia.a.present.activity.auth.su.SignUpActivity
import com.datn.bia.a.present.activity.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    override fun inflateBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun clickViews() {
        super.clickViews()

        binding.icSetting.click {
            startActivity(Intent(requireActivity(), SettingActivity::class.java))
        }

        binding.btnSignUp.click {
            startActivity(Intent(requireActivity(), SignUpActivity::class.java))
        }

        binding.btnSignIn.click {
            startActivity(Intent(requireActivity(), SignInActivity::class.java))
        }
    }
}