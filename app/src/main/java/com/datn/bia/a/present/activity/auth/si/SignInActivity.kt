package com.datn.bia.a.present.activity.auth.si

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivitySignInBinding
import com.datn.bia.a.present.activity.auth.su.SignUpActivity
import com.datn.bia.a.present.activity.home.MainActivity
import com.datn.bia.a.present.dialog.LoadingDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()

    private var loadingDialog: LoadingDialog? = null

    override fun getLayoutActivity(): Int = R.layout.activity_sign_in

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
                        val json = Gson().toJson(state.data)
                        SharedPrefCommon.jsonAcc = json ?: ""

                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finishAffinity()

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