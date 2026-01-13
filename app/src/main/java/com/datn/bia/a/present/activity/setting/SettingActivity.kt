package com.datn.bia.a.present.activity.setting

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivitySettingBinding
import com.datn.bia.a.domain.model.domain.SettingCat
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.present.activity.fo.language.LanguageActivity
import com.datn.bia.a.present.activity.home.MainActivity
import com.datn.bia.a.present.activity.setting.adapter.SettingCatAdapter
import com.datn.bia.a.present.dialog.LoadingDialog
import com.datn.bia.a.present.dialog.UpdateDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    private var settingCatAdapter: SettingCatAdapter? = null
    private var updatePhoneDialog: UpdateDialog? = null
    private var loadingDialog: LoadingDialog? = null
    private var updateAddressDialog: UpdateDialog? = null
    private var cachePhoneNumber: String = ""
    private var cacheAddress: String = ""

    private val viewModel: SettingViewModel by viewModels()

    override fun getLayoutActivity(): Int = R.layout.activity_setting

    override fun initViews() {
        super.initViews()

        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            binding.btnLogOut.goneView()
        } else {
            binding.btnLogOut.visibleView()
        }

        Log.d("duylt", "Json: ${SharedPrefCommon.jsonAcc}")

        initRcvSetting()
        updatePhoneDialog = UpdateDialog(
            this,
            getString(R.string.update_phone),
            onUpdate = { message ->
                if (message.isEmpty()) {
                    return@UpdateDialog
                }

                cachePhoneNumber = message
                viewModel.updatePhone(message)
            }, onClose = {

            }
        )
        updateAddressDialog = UpdateDialog(
            this,
            getString(R.string.update_address),
            onUpdate = { address ->
                if (address.isEmpty()) {
                    return@UpdateDialog
                }

                cacheAddress = address
                viewModel.updateAddress(address)
            }, onClose = {}
        )
        loadingDialog = LoadingDialog(this)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }

        binding.icChat.click {

        }

        binding.btnLogOut.click {
            SharedPrefCommon.jsonAcc = ""
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.stateUpdatePhone.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        loadingDialog?.cancel()
                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success<*> -> {
                        Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)
                            ?.let {
                                val newRes = it.copy(
                                    user = it.user?.copy(
                                        phone = cachePhoneNumber
                                    )
                                )

                                SharedPrefCommon.jsonAcc = Gson().toJson(newRes)
                            }

                        showToastOnce(getString(R.string.update_success))
                        loadingDialog?.cancel()

                        viewModel.changeStateToIdle()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.stateUpdateAddress.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        loadingDialog?.cancel()
                        viewModel.changeStateAddressToIdle()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success<*> -> {
                        Gson().fromJson(SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java)
                            ?.let {
                                val newRes = it.copy(
                                    user = it.user?.copy(
                                        address = cacheAddress
                                    )
                                )

                                SharedPrefCommon.jsonAcc = Gson().toJson(newRes)
                            }

                        showToastOnce(getString(R.string.update_success))
                        loadingDialog?.cancel()

                        viewModel.changeStateAddressToIdle()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        updatePhoneDialog?.dismiss()
        updatePhoneDialog = null

        super.onDestroy()
    }

    private fun initRcvSetting() = binding.rcvSettingCat.apply {
        settingCatAdapter = SettingCatAdapter(
            contextParams = this@SettingActivity,
            onSettingItem = { index, setting ->
                when (setting.id) {
                    0 -> {

                    }

                    1 -> updateAddressDialog?.show()

                    2 -> updatePhoneDialog?.show()

                    10 ->
                        startActivity(Intent(this@SettingActivity, LanguageActivity::class.java))
                }
            }
        ).apply {
            submitData(SettingCat.getAllSettingsCat())
        }

        adapter = settingCatAdapter
    }

}