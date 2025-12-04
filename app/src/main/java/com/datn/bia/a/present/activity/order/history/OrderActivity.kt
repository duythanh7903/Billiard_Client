package com.datn.bia.a.present.activity.order.history

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.databinding.ActivityOrderBinding
import com.datn.bia.a.domain.model.domain.OrderState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderActivity : BaseActivity<ActivityOrderBinding>() {

    private val viewModel: OrderViewModel by viewModels()

    private var orderAdapter: OrderAdapter? = null
    private var orderStateAdapter: OrderStateAdapter? = null

    private val onPageChangeCbListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            binding.rcvStatusOrder.scrollToPosition(position)
            orderStateAdapter?.indexSelect = position
        }
    }

    override fun getLayoutActivity(): Int = R.layout.activity_order

    override fun initViews() {
        super.initViews()

        binding.vpgTab.apply {
            orderAdapter = OrderAdapter(this@OrderActivity)
            adapter = orderAdapter
            registerOnPageChangeCallback(onPageChangeCbListener)
        }

        binding.rcvStatusOrder.apply {
            orderStateAdapter = OrderStateAdapter(
                contextParams = this@OrderActivity,
                onStatusClick = { index, status ->
                    orderStateAdapter?.indexSelect = index
                    binding.vpgTab.currentItem = index
                }
            ).apply {
                submitData(OrderState.getListStatus())
            }

            adapter = orderStateAdapter
        }

        binding.vpgTab.currentItem =
            intent.getIntExtra(AppConst.KEY_ORDER_TYPE, 0)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect { orderState ->
                when (val response = orderState.uiState) {
                    is UiState.Error -> {
                        showToastOnce(response.message)

                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> Unit

                    UiState.Loading -> Unit

                    is UiState.Success -> {
                        viewModel.cacheListOrder(response.data)

                        viewModel.changeStateToIdle()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        binding.vpgTab.unregisterOnPageChangeCallback(onPageChangeCbListener)
        orderAdapter = null
        orderStateAdapter?.list?.clear()
        orderStateAdapter = null

        super.onDestroy()
    }
}