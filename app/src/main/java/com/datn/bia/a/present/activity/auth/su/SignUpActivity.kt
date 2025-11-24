package com.datn.bia.a.present.activity.auth.su

import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.isValidEmailAndroid
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.databinding.ActivitySignUpBinding
import com.datn.bia.a.present.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private val viewModel: SignUpViewModel by viewModels()

    private var loadingDialog: LoadingDialog? = null

    override fun getLayoutActivity(): Int = R.layout.activity_sign_up

    override fun initViews() {
        super.initViews()

        loadingDialog = LoadingDialog(this)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.tvSignIn.click {
            finish()
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
            ) = viewModel.changeEmailValue(s.toString())
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
            ) = viewModel.changePasswordValue(s.toString())
        })

        binding.edtPasswordConfirmation.addTextChangedListener(object : TextWatcher {
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
            ) = viewModel.changeConfirmPasswordValue(s.toString())
        })

        binding.edtUsername.addTextChangedListener(object : TextWatcher {
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
            ) = viewModel.changeUsernameValue(s.toString())
        })

        binding.btnSignUp.click {
            onSignUpEvent()
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect { signUpState ->
                when (val uiState = signUpState.uiState) {
                    is UiState.Error -> {
                        showToastOnce(uiState.message)
                        loadingDialog?.cancel()
                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> {
                        loadingDialog?.cancel()
                    }

                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success -> {
                        viewModel.changeStateToIdle()
                        loadingDialog?.cancel()

                        finish()
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

    private fun onSignUpEvent() {
        if (viewModel.state.value.email.isEmpty() ||
            viewModel.state.value.password.isEmpty() ||
            viewModel.state.value.confirmPassword.isEmpty() ||
            viewModel.state.value.username.isEmpty()
        ) {
            showToastOnce(getString(R.string.msg_input_null))
            return
        }

        if (!viewModel.state.value.email.isValidEmailAndroid()) {
            showToastOnce(getString(R.string.msg_email_is_not_invalid))
            return
        }

        if (viewModel.state.value.password.length < 8) {
            showToastOnce(getString(R.string.msg_password_least_8_char))
            return
        }

        if (viewModel.state.value.password != viewModel.state.value.confirmPassword) {
            showToastOnce(getString(R.string.msg_password_not_match))
            return
        }

        viewModel.onSignUpEvent()
    }
}