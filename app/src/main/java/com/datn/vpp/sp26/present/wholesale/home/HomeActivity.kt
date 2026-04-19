package com.datn.vpp.sp26.present.wholesale.home

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.application.GlobalApp
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.common.toListProductDataDTO
import com.datn.vpp.sp26.common.toListResCatDTO
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityHomeBinding
import com.datn.vpp.sp26.present.user.activity.search.SearchActivity
import com.datn.vpp.sp26.present.user.fragment.home.adapter.CatAdapter
import com.datn.vpp.sp26.present.user.fragment.home.adapter.ProductAdapter
import com.datn.vpp.sp26.present.wholesale.cart.CartActivity
import com.datn.vpp.sp26.present.wholesale.order.OrderActivity
import com.datn.vpp.sp26.present.wholesale.product.ProductActivity
import com.datn.vpp.sp26.present.wholesale.profile.ProfileActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private var catAdapter: CatAdapter? = null
    private var productAdapter: ProductAdapter? = null
    private var gson: Gson? = null

    override fun getLayoutActivity(): Int = R.layout.activity_home

    override fun initViews() {
        super.initViews()

        gson = Gson()

        binding.rcvCategory.apply {
            catAdapter = CatAdapter(
                onClick = { index, cat ->
                    val index = catAdapter?.changeIndexSelect(index) ?: -1
                    if (index == -1) {
                        productAdapter?.submitData(
                            viewModel.allPro.value
                        )
                    } else {
                        productAdapter?.submitData(
                            viewModel.allPro.value.filter { it.category?.id == viewModel.allCat.value[index].id }
                        )
                    }
                }
            )

            adapter = catAdapter
        }

        binding.rcvProduct.apply {
            productAdapter = ProductAdapter(
                contextParams = this@HomeActivity,
                isBusinessAccount = SharedPrefCommon.role == AppConst.ROLE_WHOLESALE,
                onProductClick = { index, product ->
                    gson?.toJson(product)?.let { json ->
                        startActivity(
                            Intent(
                                this@HomeActivity,
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

        binding.btnSearch.click {
            onSearchEvent()
        }

        binding.icProfile.click {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.icCart.click {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.icOrder.click {
            startActivity(Intent(this, OrderActivity::class.java))
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.stateProduct.collect {
                val list = it.toListProductDataDTO()

                productAdapter?.submitData(list)
                viewModel.cacheAllPro(list)
                viewModel.changeProductStateToIdle()
            }
        }

        lifecycleScope.launch {
            viewModel.stateCate.collect {
                val list = it.toListResCatDTO()

                catAdapter?.submitData(list)
                viewModel.cacheAllCat(list)
                viewModel.changeCatStateToIdle()
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { homeState ->
                when (val uiState = homeState.productState) {
                    is UiState.Error -> {
                        binding.loadingBar.goneView()
                        binding.rcvProduct.visibleView()

                        showToastOnce(uiState.message)
                        viewModel.changeProductStateToIdle()
                    }

                    UiState.Idle -> {

                    }

                    UiState.Loading -> {
                        binding.loadingBar.visibleView()
                        binding.rcvProduct.goneView()
                    }

                    is UiState.Success -> {
                        binding.loadingBar.goneView()
                        binding.rcvProduct.visibleView()

                        viewModel.changeProductStateToIdle()
                    }
                }

                when (homeState.catState) {
                    is UiState.Error -> {
                        viewModel.changeCatStateToIdle()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onDestroy() {
        catAdapter?.list?.clear()
        catAdapter = null
        productAdapter?.list?.clear()
        productAdapter = null
        gson = null

        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        if (GlobalApp.jsonSearchResult.isNotEmpty()) {
            startActivity(
                Intent(
                    this,
                    ProductActivity::class.java
                ).apply {
                    putExtra(AppConst.KEY_PRODUCT_DETAIL, GlobalApp.jsonSearchResult)
                })
            GlobalApp.jsonSearchResult = ""
        }
    }

    private fun onSearchEvent() {
        startActivity(Intent(this, SearchActivity::class.java))
    }
}