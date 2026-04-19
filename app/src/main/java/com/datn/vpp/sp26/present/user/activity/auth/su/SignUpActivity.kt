package com.datn.vpp.sp26.present.user.activity.auth.su

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.isValidEmailAndroid
import com.datn.vpp.sp26.common.base.ext.setTextColorById
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.databinding.ActivityRegisterBinding
import com.datn.vpp.sp26.present.user.activity.auth.si.SignInActivity
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivityRegisterBinding>() {

    private var isFromProfile: Boolean = false

    private val viewModel: SignUpViewModel by viewModels()

    private var loadingDialog: LoadingDialog? = null

    private var tag: Int = 0

    override fun getLayoutActivity(): Int = R.layout.activity_register

    override fun initViews() {
        super.initViews()

        isFromProfile = intent.getBooleanExtra(AppConst.KEY_FROM_PROFILE, false)
        loadingDialog = LoadingDialog(this)

        Glide.with(this).load(R.drawable.ic_org_active).into(binding.icTabOrg)
        Glide.with(this).load(R.drawable.ic_profile_un_select).into(binding.icTabPer)
        binding.tvOrg.setTextColorById(R.color.white)
        binding.tvPer.setTextColorById(R.color.textGray)
        binding.tabOrg.setBackgroundResource(R.drawable.bg_tab_1)
        binding.tabPersonal.setBackgroundResource(R.drawable.bg_tab_trans)
        binding.edtUsername.goneView()
        binding.edtPhoneNumber.visibleView()
        tag = 0
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.tabOrg.click {
            Glide.with(this).load(R.drawable.ic_org_active).into(binding.icTabOrg)
            Glide.with(this).load(R.drawable.ic_profile_un_select).into(binding.icTabPer)
            binding.tvOrg.setTextColorById(R.color.white)
            binding.tvPer.setTextColorById(R.color.textGray)
            binding.tabOrg.setBackgroundResource(R.drawable.bg_tab_1)
            binding.tabPersonal.setBackgroundResource(R.drawable.bg_tab_trans)
            binding.edtUsername.goneView()
            binding.edtPhoneNumber.visibleView()
            binding.edtUsername.text.clear()
            binding.edtUsername.text.clear()
            binding.edtPhoneNumber.text.clear()

            tag = 0
        }

        binding.tabPersonal.click {
            Glide.with(this).load(R.drawable.ic_org_unactive).into(binding.icTabOrg)
            Glide.with(this).load(R.drawable.ic_profile_selected_2).into(binding.icTabPer)
            binding.tvOrg.setTextColorById(R.color.textGray)
            binding.tvPer.setTextColorById(R.color.white)
            binding.tabOrg.setBackgroundResource(R.drawable.bg_tab_trans)
            binding.tabPersonal.setBackgroundResource(R.drawable.bg_tab_2)
            binding.edtUsername.visibleView()
            binding.edtPhoneNumber.goneView()
            binding.edtUsername.text.clear()
            binding.edtPhoneNumber.text.clear()

            tag = 1
        }

        binding.tvSignIn.click {
            if (isFromProfile) {
                startActivity(Intent(this, SignInActivity::class.java))
                finishAffinity()
            } else {
                finish()
            }
        }

        binding.edtPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) = Unit

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) = viewModel.changePhoneNumberValue(p0?.toString() ?: "")
        })

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
            ) = viewModel.changeEmailValue(s?.toString() ?: "")
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
            ) = viewModel.changePasswordValue(s?.toString() ?: "")
        })

        binding.edtConfirmPassword.addTextChangedListener(object : TextWatcher {
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
            ) = viewModel.changeConfirmPasswordValue(s?.toString() ?: "")
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
            ) = viewModel.changeUsernameValue(s?.toString() ?: "")
        })

        binding.btnSignUp.click {
            onSignUpEvent(tag)
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

                        if (!isFromProfile) finish()
                        else {
                            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                            finishAffinity()
                        }
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

    private fun onSignUpEvent(tag: Int) {
        val isFirstFieldEmpty =
            if (tag == 0) binding.edtPhoneNumber.text.toString().trim().isEmpty()
            else binding.edtUsername.text.toString().trim().isEmpty()

        if (viewModel.state.value.email.isEmpty() ||
            viewModel.state.value.password.isEmpty() ||
            viewModel.state.value.confirmPassword.isEmpty() ||
            isFirstFieldEmpty
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

        viewModel.onSignUpEvent(tag)
    }
}