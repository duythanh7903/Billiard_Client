package com.datn.bia.a.present.activity.voucher

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.databinding.ActivityVouchersBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VouchersActivity : BaseActivity<ActivityVouchersBinding>() {

    private val viewModel: VoucherViewModel by viewModels()

    private var voucherAdapter: VoucherAdapter? = null

    override fun getLayoutActivity(): Int = R.layout.activity_vouchers

    override fun initViews() {
        super.initViews()

        binding.rcvVoucher.apply {
            voucherAdapter = VoucherAdapter(
                onSelected = { index, voucher ->
                    voucherAdapter?.indexSelect = index

                    binding.tvCountVoucher.visibleView()
                    binding.tvMsgVoucherApplied.visibleView()
                }
            )

            adapter = voucherAdapter
        }

        binding.tvCountVoucher.goneView()
        binding.tvMsgVoucherApplied.goneView()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click {
            finish()
        }

        binding.btnApplyVoucher.click {
            voucherAdapter?.getVoucherSelect()?.let { voucher ->
                setResult(RESULT_OK, Intent().apply {
                    putExtra(AppConst.KEY_VOUCHER, Gson().toJson(voucher))
                })
                finish()
            } ?: run {

            }
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect { uiState ->
                when (val response = uiState.uiState) {
                    is UiState.Error -> {
                        showToastOnce(response.message)
                        viewModel.changeStateToIdle()

                        binding.loadingView.goneView()
                        binding.rcvVoucher.goneView()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        binding.loadingView.visibleView()
                        binding.rcvVoucher.goneView()
                    }

                    is UiState.Success -> {
                        binding.loadingView.goneView()
                        binding.rcvVoucher.visibleView()

                        val listVouchers = response.data
                        voucherAdapter?.submitData(listVouchers)

                        viewModel.changeStateToIdle()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        voucherAdapter?.list?.clear()
        voucherAdapter = null

        super.onDestroy()
    }
}