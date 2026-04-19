package com.datn.vpp.sp26.present.user.activity.auth.si

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityLoginBinding
import com.datn.vpp.sp26.present.user.activity.auth.pass.ForgotPassActivity
import com.datn.vpp.sp26.present.user.activity.auth.su.SignUpActivity
import com.datn.vpp.sp26.present.user.activity.home.MainActivity
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import com.datn.vpp.sp26.present.wholesale.home.HomeActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    private var loadingDialog: LoadingDialog? = null

    override fun getLayoutActivity(): Int = R.layout.activity_login

    override fun initViews() {
        super.initViews()

        loadingDialog = LoadingDialog(this)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnSignIn.click { onSignInEvent() }

        binding.tvSignUp.click {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) = viewModel.changeEmailValue(s?.toString()?.trim() ?: "")
        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) = viewModel.changePasswordValue(s?.toString()?.trim() ?: "")
        })

        binding.tvForgotPassword.click {
            startActivity(Intent(this, ForgotPassActivity::class.java))
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect { signInState ->
                when (val state = signInState.uiState) {
                    is UiState.Error -> {
                        loadingDialog?.dismiss()
                        showToastOnce(state.message)
                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> loadingDialog?.dismiss()
                    UiState.Loading -> loadingDialog?.show()
                    is UiState.Success -> {
                        loadingDialog?.dismiss()
                        val data = state.data
                        val json = Gson().toJson(data)
                        SharedPrefCommon.jsonAcc = json ?: ""
                        SharedPrefCommon.token = state.data.token ?: ""
                        SharedPrefCommon.idUser = state.data.user?.id ?: ""
                        SharedPrefCommon.role = state.data.user?.role ?: AppConst.ROLE_USER
                        Log.d("debug", "${state.data.user?.id}")
                        Log.d("debug", "Share: ${SharedPrefCommon.idUser}")

                        if (SharedPrefCommon.role == AppConst.ROLE_USER) {
                            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                            finishAffinity()
                        } else {
                            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                            finishAffinity()
                        }

                        viewModel.changeStateToIdle()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        loadingDialog?.dismiss()
        loadingDialog = null

        super.onDestroy()
    }

    private fun onSignInEvent() {
        if (viewModel.state.value.email.isEmpty() || viewModel.state.value.password.isEmpty()) {
            showToastOnce(getString(R.string.msg_input_null))
            return
        }

        viewModel.onSignInEvent()
    }
}