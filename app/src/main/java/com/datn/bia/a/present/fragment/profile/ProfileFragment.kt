package com.datn.bia.a.present.fragment.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.UiState
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
import com.datn.bia.a.present.activity.order.history.OrderActivity
import com.datn.bia.a.present.activity.setting.SettingActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

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

        binding.btnPendingConfirmation.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
                requireActivity().finishAffinity()
                return@click
            }

            startActivity(Intent(requireContext(), OrderActivity::class.java))
        }

        binding.btnPendingPickup.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
                requireActivity().finishAffinity()
                return@click
            }

            startActivity(Intent(requireContext(), OrderActivity::class.java))
        }

        binding.btnPendingShipping.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
                requireActivity().finishAffinity()
                return@click
            }

            startActivity(Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(AppConst.KEY_ORDER_TYPE, 1)
            })
        }

        binding.tvPurchaseHistory.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(requireContext(), SignInActivity::class.java))
                requireActivity().finishAffinity()
                return@click
            }

            startActivity(Intent(requireContext(), OrderActivity::class.java).apply {
                putExtra(AppConst.KEY_ORDER_TYPE, 2)
            })
        }
    }

    @SuppressLint("SetTextI18n")
    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.tvCountItemInCart.text =
                    "${state.listCart.sumOf { it.quantity }} ${
                        getString(
                            if (state.listCart.sumOf { it.quantity } > 1) R.string.carts else R.string.cart
                        )
                    }"

                when (val response = state.uiState) {
                    is UiState.Error -> {
                        binding.tvCountItemHistory.goneView()
                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        binding.tvCountItemHistory.goneView()
                    }

                    is UiState.Success -> {
                        val res = response.data.size
                        binding.tvCountItemHistory.apply {
                            text = "$res ${
                                getString(
                                    if (res > 1) R.string.orders else R.string.order
                                )
                            }"
                            visibleView()
                        }

                        viewModel.changeStateToIdle()
                    }
                }
            }
        }
    }
}