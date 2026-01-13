package com.datn.bia.a.present.fragment.home

import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.application.GlobalApp
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseFragment
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.common.toListProductDataDTO
import com.datn.bia.a.common.toListResCatDTO
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.FragmentHomeBinding
import com.datn.bia.a.present.activity.auth.si.SignInActivity
import com.datn.bia.a.present.activity.prod.ProductActivity
import com.datn.bia.a.present.activity.search.SearchActivity
import com.datn.bia.a.present.fragment.home.adapter.CatAdapter
import com.datn.bia.a.present.fragment.home.adapter.ProductAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private var catAdapter: CatAdapter? = null
    private var productAdapter: ProductAdapter? = null
    private var gson: Gson? = null

    override fun inflateBinding(): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater)

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
                contextParams = requireContext(),
                onProductClick = { index, product ->
                    gson?.toJson(product)?.let { json ->
                        startActivity(
                            Intent(
                                requireContext(),
                                ProductActivity::class.java
                            ).apply {
                                putExtra(AppConst.KEY_PRODUCT_DETAIL, json)
                            })
                    } ?: run {
                        requireContext().showToastOnce(getString(R.string.msg_wrong))
                    }
                }
            )

            adapter = productAdapter
        }
    }

    override fun clickViews() {
        super.clickViews()

        binding.icChat.click {
            onChatEvent()
        }

        binding.btnSearch.click {
            onSearchEvent()
        }

        binding.icNotification.click {
            onNotificationEvent()
        }
    }

    override fun observeData() {
        super.observeData()

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

                        requireContext().showToastOnce(uiState.message)
                        Log.d("duylt", "Error: ${uiState.message}")
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
                    requireContext(),
                    ProductActivity::class.java
                ).apply {
                    putExtra(AppConst.KEY_PRODUCT_DETAIL, GlobalApp.jsonSearchResult)
                })
            GlobalApp.jsonSearchResult = ""
        }
    }

    private fun onChatEvent() {
        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            startActivity(Intent(requireContext(), SignInActivity::class.java))
            return
        }
    }

    private fun onSearchEvent() {
        startActivity(Intent(requireContext(), SearchActivity::class.java))
    }

    private fun onNotificationEvent() {

    }
}