package com.datn.bia.a.present.activity.favorite

import android.annotation.SuppressLint
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
import com.datn.bia.a.databinding.ActivityFavoriteProductsBinding
import com.datn.bia.a.present.activity.prod.ProductActivity
import com.datn.bia.a.present.fragment.home.adapter.ProductAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteProductsActivity : BaseActivity<ActivityFavoriteProductsBinding>() {

    private val viewModel: FavoriteViewModel by viewModels()

    private var productAdapter: ProductAdapter? = null

    override fun getLayoutActivity(): Int = R.layout.activity_favorite_products

    override fun initViews() {
        super.initViews()

        binding.rcvFavorite.apply {
            productAdapter = ProductAdapter(
                contextParams = this@FavoriteProductsActivity,
                onProductClick = { index, product ->
                    Gson().toJson(product)?.let { json ->
                        startActivity(
                            Intent(
                                this@FavoriteProductsActivity,
                                ProductActivity::class.java
                            ).apply {
                                putExtra(AppConst.KEY_PRODUCT_DETAIL, json)
                            })
                    } ?: run {
                        showToastOnce(getString(R.string.msg_wrong))
                    }
                }
            )

            adapter = productAdapter
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icChat.click {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (val response = state.uiState) {
                    is UiState.Error -> {
                        showToastOnce(response.message)

                        binding.loadingView.goneView()
                        binding.rcvFavorite.goneView()

                        viewModel.changeStateToIdle()
                    }

                    UiState.Idle -> {}
                    UiState.Loading -> {
                        binding.loadingView.visibleView()
                        binding.rcvFavorite.goneView()
                        binding.tvCountItemFavorite.goneView()
                    }

                    is UiState.Success -> {
                        binding.loadingView.goneView()
                        binding.rcvFavorite.visibleView()
                        binding.tvCountItemFavorite.visibleView()

                        val listFavorite =
                            viewModel.state.value.listFavorite.map { it.productId }.toSet()
                        val data = response.data.data ?: emptyList()
                        val listSubmit = data.filter { it.id in listFavorite }
                        productAdapter?.submitData(listSubmit)
                        binding.tvCountItemFavorite.text = "(${listSubmit.size})"

                        viewModel.changeStateToIdle()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        productAdapter?.list?.clear()
        productAdapter = null

        super.onDestroy()
    }
}