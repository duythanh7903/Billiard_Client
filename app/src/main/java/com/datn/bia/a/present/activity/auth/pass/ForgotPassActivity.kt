package com.datn.bia.a.present.activity.auth.pass

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.databinding.ActivityForgotpassBinding
import com.datn.bia.a.domain.model.dto.req.ReqForgotPass
import com.datn.bia.a.present.activity.auth.pass.ResetPassActivity
import com.datn.bia.a.present.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPassActivity : BaseActivity<ActivityForgotpassBinding>() {

    private val viewModel: ForgotViewModel by viewModels()

    private var loadingDialog: LoadingDialog? = null

    override fun getLayoutActivity(): Int = R.layout.activity_forgotpass

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
                        showToastOnce(uiState.message.ifEmpty { getString(R.string.msg_email_not_exists) })
                    }

                    UiState.Idle -> {}
                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success -> {
                        loadingDialog?.show()
                        startActivity(
                            Intent(
                                this@ForgotPassActivity,
                                ResetPassActivity::class.java
                            )
                        )
                        finish()
                    }
                }
            }
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnSend.click {
            val email = binding.edtEmail.text.toString().trim()

            if (email.isEmpty()) {
                showToastOnce(getString(R.string.msg_input_null))
                return@click
            }

            viewModel.forgotPass(
                ReqForgotPass(
                    email = email
                )
            )
        }

        binding.icBack.click {
            finish()
        }
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null

        super.onDestroy()
    }
}