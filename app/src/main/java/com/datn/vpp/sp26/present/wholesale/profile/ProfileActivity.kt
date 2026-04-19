package com.datn.vpp.sp26.present.wholesale.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityProfileBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.present.user.activity.auth.si.SignInActivity
import com.datn.vpp.sp26.present.user.activity.home.MainActivity
import com.datn.vpp.sp26.present.user.activity.order.history.OrderActivity
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private var loadingDialog: LoadingDialog? = null

    private val viewModel: ProfileViewModel by viewModels()

    override fun getLayoutActivity(): Int = R.layout.activity_profile

    override fun initViews() {
        super.initViews()

        loadingDialog = LoadingDialog(this)
        setData()

        Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)?.let { user ->
            binding.edtAddress.setText(user.user?.address ?: "")
            binding.edtPhoneNumber.setText(user.user?.phone ?: "")
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }

        binding.btnLogOut.click {
            SharedPrefCommon.jsonAcc = ""
            SharedPrefCommon.token = ""
            SharedPrefCommon.idUser = ""
            SharedPrefCommon.role = ""
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        binding.btnPendingConfirmation.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(this@ProfileActivity, SignInActivity::class.java))
                finishAffinity()
                return@click
            }

            startActivity(Intent(this@ProfileActivity, OrderActivity::class.java))
        }

        binding.btnPendingPickup.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(this@ProfileActivity, SignInActivity::class.java))
                finishAffinity()
                return@click
            }

            startActivity(Intent(this@ProfileActivity, OrderActivity::class.java))
        }

        binding.btnPendingShipping.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(this@ProfileActivity, SignInActivity::class.java))
                finishAffinity()
                return@click
            }

            startActivity(Intent(this@ProfileActivity, OrderActivity::class.java).apply {
                putExtra(AppConst.KEY_ORDER_TYPE, 1)
            })
        }

        binding.tvPurchaseHistory.click {
            if (SharedPrefCommon.jsonAcc.isEmpty()) {
                startActivity(Intent(this@ProfileActivity, SignInActivity::class.java))
                finishAffinity()
                return@click
            }

            startActivity(Intent(this@ProfileActivity, OrderActivity::class.java).apply {
                putExtra(AppConst.KEY_ORDER_TYPE, 2)
            })
        }

        binding.btnSend.click {
            onUpdateProfile()
        }

        binding.btnReqExport.click {

        }
    }

    @SuppressLint("SetTextI18n")
    override fun observerData() {
        super.observerData()

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

        lifecycleScope.launch {
            viewModel.stateOrder.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        loadingDialog?.dismiss()
                        showToastOnce(state.message)
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success -> {
                        loadingDialog?.dismiss()

                        Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)
                            ?.let {
                                val newRes = it.copy(
                                    user = it.user?.copy(
                                        address = binding.edtAddress.text.toString().trim(),
                                        phone = binding.edtPhoneNumber.text.toString().trim()
                                    )
                                )

                                SharedPrefCommon.jsonAcc = Gson().toJson(newRes)
                            }
                    }
                }
            }
        }
    }

    private fun setData() {
        val jsonUser = SharedPrefCommon.jsonAcc
        val resLogin = Gson().fromJson(jsonUser, ResLoginUserDTO::class.java)
        val user = resLogin?.user

        if (resLogin == null || user == null) {
            finish()
            return
        }

        binding.tvUserName.text = user.email
    }

    private fun onUpdateProfile() {
        val phoneNumber: String = binding.edtPhoneNumber.text.toString().trim()
        val address = binding.edtAddress.text.toString().trim()

        if (phoneNumber.isEmpty() || address.isEmpty()) {
            return
        }

        viewModel.updatePhoneNumberAndAddress(
            id = SharedPrefCommon.idUser,
            phone = phoneNumber,
            address = address
        )
    }
}