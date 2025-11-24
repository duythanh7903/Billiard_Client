package com.datn.bia.a.present.activity.prod

import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.databinding.ActivityProductBinding
import com.datn.bia.a.domain.model.dto.res.ResProductDataDTO
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity<ActivityProductBinding>() {

    private val viewModel: ProductViewModel by viewModels()

    private var gson: Gson? = null
    private var imageAdapter: ImageAdapter? = null
    private var idProdCur: String? = null

    override fun getLayoutActivity(): Int = R.layout.activity_product

    override fun initViews() {
        super.initViews()

        gson = Gson()
        receiveData()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }

        binding.btnAddCart.click { onAddCartEvent() }
    }

    override fun onDestroy() {
        gson = null
        imageAdapter?.list?.clear()
        imageAdapter = null

        super.onDestroy()
    }

    private fun receiveData() {
        intent.getStringExtra(AppConst.KEY_PRODUCT_DETAIL)?.let { json ->
            gson?.fromJson(json, ResProductDataDTO::class.java)?.let { product ->
                onShowData(product)
            } ?: run {
                showToastOnce(getString(R.string.msg_wrong))
                finish()
            }
        } ?: run {
            showToastOnce(getString(R.string.msg_wrong))
            finish()
        }
    }

    private fun onShowData(prod: ResProductDataDTO) = binding.apply {
        imageAdapter = ImageAdapter(
            contextParams = this@ProductActivity,
            onItemClicked = { linkUrl ->
                Glide.with(this@ProductActivity).load(linkUrl).into(binding.imgProduct)
            }
        ).apply { submitData(prod.albumImage ?: emptyList()) }

        rcvImage.adapter = imageAdapter
        Glide.with(this@ProductActivity).load(prod.imageUrl).into(binding.imgProduct)
        tvProductName.text = prod.name ?: ""
        tvDes.text = prod.des ?: ""

        idProdCur = prod.id
    }

    private fun onAddCartEvent() {
        idProdCur?.let { id ->
            viewModel.addProductToCart(id)
            showToastOnce(getString(R.string.msg_added_to_cart))
        } ?: run {
            showToastOnce(getString(R.string.msg_wrong))
        }
    }
}