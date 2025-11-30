package com.datn.bia.a.present.fragment.profile

import android.content.Intent
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.base.BaseFragment
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.FragmentProfileBinding
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.present.activity.auth.si.SignInActivity
import com.datn.bia.a.present.activity.auth.su.SignUpActivity
import com.datn.bia.a.present.activity.favorite.FavoriteProductsActivity
import com.datn.bia.a.present.activity.setting.SettingActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun inflateBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            binding.tvUserName.text = getString(R.string.unknown)
            binding.tvCountItemInCart.text = getString(R.string._cart, 0)
            binding.tvCountItemHistory.text = getString(R.string._order, 0)

            binding.btnSignIn.visibleView()
            binding.btnSignUp.visibleView()
            binding.icSetting.goneView()
            binding.icChat.goneView()
        } else {
            val obj = Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)

            if (obj != null) {
                binding.btnSignIn.goneView()
                binding.btnSignUp.goneView()
                binding.icSetting.visibleView()
                binding.icChat.visibleView()

                binding.tvUserName.text = obj.user?.username ?: getString(R.string.unknown)
                binding.tvCountItemInCart.text = getString(R.string._cart, 0)
                binding.tvCountItemHistory.text = getString(R.string._order, 0)
            } else {
                binding.tvUserName.text = getString(R.string.unknown)
                binding.tvCountItemInCart.text = getString(R.string._cart, 0)
                binding.tvCountItemHistory.text = getString(R.string._order, 0)

                binding.btnSignIn.visibleView()
                binding.btnSignUp.visibleView()
                binding.icSetting.goneView()
                binding.icChat.goneView()
            }
        }
    }

    override fun clickViews() {
        super.clickViews()

        binding.icSetting.click {
            startActivity(Intent(requireActivity(), SettingActivity::class.java))
        }

        binding.btnSignUp.click {
            startActivity(Intent(requireActivity(), SignUpActivity::class.java).apply {
                putExtra(AppConst.KEY_FROM_PROFILE, true)
            })
        }

        binding.btnSignIn.click {
            startActivity(Intent(requireActivity(), SignInActivity::class.java))
            requireActivity().finishAffinity()
        }

        binding.btnLiked.click {
            startActivity(Intent(requireContext(), FavoriteProductsActivity::class.java))
        }
    }
}