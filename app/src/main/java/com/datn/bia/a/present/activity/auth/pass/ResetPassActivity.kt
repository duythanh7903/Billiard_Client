package com.datn.bia.a.present.activity.auth.pass

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.databinding.ActivityResetPassBinding
import com.datn.bia.a.domain.model.dto.req.ReqResetPass
import com.datn.bia.a.present.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPassActivity : BaseActivity<ActivityResetPassBinding>() {

    private var loadingDialog: LoadingDialog? = null

    private val viewModel: ResetPassViewModel by viewModels()

    override fun getLayoutActivity(): Int = R.layout.activity_reset_pass

    override fun initViews() {
        super.initViews()

        loadingDialog = LoadingDialog(this)
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> {
                        loadingDialog?.dismiss()
                        showToastOnce(uiState.message)
                    }
                    UiState.Idle -> {}
                    UiState.Loading -> {
                        loadingDialog?.show()
                    }
                    is UiState.Success -> {
                        loadingDialog?.dismiss()
                        finish()
                        showToastOnce(uiState.data.message?.ifEmpty { "Đổi mật khẩu thành công" } ?: "Đổi mật khẩu thành công")
                    }
                }
            }
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnResetPassword.click {
            val code = binding.edtCode.text.toString().trim()
            val pass = binding.edtNewPassword.text.toString().trim()
            val confirm = binding.edtConfirmNewPassword.text.toString().trim()

            if (code.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                showToastOnce(getString(R.string.msg_input_null))
                return@click
            }

            if (pass != confirm) {
                showToastOnce(getString(R.string.msg_password_not_match))
                return@click
            }

            if (pass.length < 8) {
                showToastOnce(getString(R.string.msg_password_least_8_char))
                return@click
            }

            viewModel.handleResetPass(
                req = ReqResetPass(
                    password = pass
                ), token = code
            )
        }
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null

        super.onDestroy()
    }
}