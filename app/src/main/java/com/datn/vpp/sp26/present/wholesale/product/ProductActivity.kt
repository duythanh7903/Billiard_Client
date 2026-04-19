package com.datn.vpp.sp26.present.wholesale.product

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.formatVND
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.isNetwork
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.base.ext.toValidUrl
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityProductWholsesaleBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDataDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResVariantDTO
import com.datn.vpp.sp26.present.user.activity.auth.si.SignInActivity
import com.datn.vpp.sp26.present.user.activity.comment.reviews.ReviewsActivity
import com.datn.vpp.sp26.present.user.activity.prod.ImageAdapter
import com.datn.vpp.sp26.present.user.activity.prod.VariantAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : BaseActivity<ActivityProductWholsesaleBinding>() {

    private val viewModel: ProductViewModel by viewModels()

    private var gson: Gson? = null
    private var imageAdapter: ImageAdapter? = null
    private var idProdCur: String? = null
    private var variant: ResVariantDTO? = null
    private var price: Double = 0.0
    private var variantAdapter: VariantAdapter? = null

    override fun getLayoutActivity(): Int = R.layout.activity_product_wholsesale

    override fun initViews() {
        super.initViews()

        gson = Gson()
        receiveData()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click {
            finish()
        }

        binding.btnSeeAllComment.click {
            startActivity(Intent(this, ReviewsActivity::class.java).apply {
                putExtra(AppConst.KEY_ID_PRODUCT, idProdCur)
                variant?.let {
                    putExtra(AppConst.KEY_VARIANT, Gson().toJson(it))
                }
            })
        }

        binding.btnAddCart.click {
            onAddCartEvent()
        }
    }

    private fun receiveData() {
        intent.getStringExtra(AppConst.KEY_PRODUCT_DETAIL)?.let { json ->
            gson?.fromJson(json, ResProductDataDTO::class.java)?.let { product ->
                onShowData(product)
                viewModel.searchProductInFavorite(product.id ?: "")
            } ?: run {
                showToastOnce(getString(R.string.msg_wrong))
                finish()
            }
        } ?: run {
            showToastOnce(getString(R.string.msg_wrong))
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onShowData(prod: ResProductDataDTO) = binding.apply {
        imageAdapter = ImageAdapter(
            contextParams = this@ProductActivity,
            onItemClicked = { linkUrl, index ->
                Glide.with(this@ProductActivity).load(linkUrl.toValidUrl()).into(binding.imgProduct)
                imageAdapter?.indexSelect = index
            }
        ).apply { submitData(prod.albumImage ?: emptyList()) }

        variantAdapter = VariantAdapter { index, item ->
            variantAdapter?.indexSelect = index

            variant = item

            price = item.priceWholesale?.toDouble() ?: 0.0
            binding.tvPrice.text = price.formatVND()
            binding.tvDiscount.goneView()
            binding.tvPrice1.goneView()
            binding.line.goneView()

            binding.tvBought.text =
                item.quantity?.toString() + " " + getString(R.string.products)
        }.apply {
            submitData(prod.variants ?: emptyList())
        }

        binding.tvBought.text =
            prod.variants?.firstOrNull()?.quantity?.toString() + " " + getString(R.string.products)

        rcvImage.adapter = imageAdapter
        binding.rcvVariant.adapter = variantAdapter
        Glide.with(this@ProductActivity).load(prod.imageUrl?.toValidUrl()).into(binding.imgProduct)
        tvProductName.text = prod.name ?: ""
        tvDes.text = prod.des ?: ""

        price = prod.variants?.firstOrNull()?.priceWholesale?.toDouble() ?: 0.0
        binding.tvPrice.text = prod.variants?.firstOrNull()?.priceWholesale?.formatVND() ?: "NaN"
        binding.tvDiscount.goneView()
        binding.tvPrice1.goneView()
        binding.line.goneView()

        idProdCur = prod.id
        variant = prod.variants?.firstOrNull()
    }

    private fun onAddCartEvent() {
        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            startActivity(Intent(this, SignInActivity::class.java))
            return
        }

        if (!isNetwork()) {
            showToastOnce(getString(R.string.msg_error_network))
            return
        }

        if (price == 0.0) {
            showToastOnce(getString(R.string.msg_wrong))
            return
        }

        idProdCur?.let { id ->
            viewModel.addProductToCart(
                productId = id,
                variant!!,
                price
            )
            showToastOnce(getString(R.string.msg_added_100_to_cart))
        } ?: run {
            showToastOnce(getString(R.string.msg_wrong))
        }
    }
}