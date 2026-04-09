package com.datn.vpp.sp26.present.activity.search

import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.application.GlobalApp
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.common.toListProductDataDTO
import com.datn.vpp.sp26.databinding.ActivitySearchBinding
import com.datn.vpp.sp26.present.fragment.home.adapter.ProductAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModels()

    private var productAdapter: ProductAdapter? = null
    private var gson: Gson? = null

    override fun getLayoutActivity(): Int = R.layout.activity_search

    override fun initViews() {
        super.initViews()

        gson = Gson()
        binding.rcvProduct.apply {
            productAdapter = ProductAdapter(
                contextParams = this@SearchActivity,
                onProductClick = { index, product ->
                    gson?.toJson(product)?.let { json ->
                        GlobalApp.jsonSearchResult = json
                        finish()
                    }
                }
            )

            adapter = productAdapter
        }
    }

    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.stateProduct.collect { list ->
                binding.rcvProduct.visibleView()
                binding.loadingView.goneView()

                val listData = list.toListProductDataDTO()
                viewModel.cacheListProduct(listData)
                productAdapter?.submitData(listData)
            }
        }

        lifecycleScope.launch {
            viewModel.keySearch.collect { key ->
                if (key.isEmpty()) productAdapter?.submitData(viewModel.listProduct.first())
                else {
                    val listTemp = viewModel.listProduct.first()
                    val resultList =
                        listTemp.filter { it.name?.lowercase()?.contains(key.lowercase()) == true }

                    productAdapter?.submitData(resultList)
                }
            }
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) =
                viewModel.changeKeySearch(s?.toString())

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
            ) = Unit
        })
    }

    override fun onDestroy() {
        productAdapter?.list?.clear()
        productAdapter = null

        super.onDestroy()
    }
}