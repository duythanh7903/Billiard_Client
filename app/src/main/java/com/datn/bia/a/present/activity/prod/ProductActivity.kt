package com.datn.bia.a.present.activity.prod

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.base.BaseActivity
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.formatVND
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.isNetwork
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.ActivityProductBinding
import com.datn.bia.a.domain.model.dto.res.ResProductDataDTO
import com.datn.bia.a.present.activity.auth.si.SignInActivity
import com.datn.bia.a.present.activity.comment.reviews.ReviewsActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    @SuppressLint("SetTextI18n")
    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.favoriteEntity.collect { favorite ->
                binding.icFavorite.isActivated =
                    favorite != null
            }
        }

        lifecycleScope.launch {
            viewModel.stateGetAllComment.collect { listComment ->
                val data = listComment.filter { it.idProduct == idProdCur }
                val star = (data.sumOf { it.rating }
                    .toFloat() / if (data.count() == 0) 1 else data.count())
                binding.tvStars.text = star.toString().take(3)
            }
        }

        binding.tvBought.text = "${getString(R.string.sold)} ${listOf(0..100).random()}"
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click { finish() }

        binding.btnAddCart.click { onAddCartEvent() }

        binding.btnBuyNow.click { onEventBuyNow() }

        binding.icFavorite.click { onFavoriteEvent() }

        binding.btnSeeAllComment.click {
            startActivity(Intent(this, ReviewsActivity::class.java).apply {
                putExtra(AppConst.KEY_ID_PRODUCT, idProdCur)
            })
        }
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
                Glide.with(this@ProductActivity).load(linkUrl).into(binding.imgProduct)
                imageAdapter?.indexSelect = index
            }
        ).apply { submitData(prod.albumImage ?: emptyList()) }

        rcvImage.adapter = imageAdapter
        Glide.with(this@ProductActivity).load(prod.imageUrl).into(binding.imgProduct)
        tvProductName.text = prod.name ?: ""
        tvDes.text = prod.des ?: ""

        if (prod.discount == null || prod.discount == 0) {
            binding.tvPrice.text = prod.price?.formatVND() ?: "NaN"
            binding.tvDiscount.goneView()
            binding.tvPrice1.goneView()
            binding.line.goneView()
        } else {
            binding.tvPrice.text =
                ((prod.price ?: 0) - (prod.discount * (prod.price ?: 0) / 100)).formatVND()
            binding.tvDiscount.apply {
                visibleView()
                text = "-${prod.discount}%"
            }
            binding.tvPrice1.text = prod.price?.formatVND() ?: "NaN"
            binding.line.visibleView()
        }

        idProdCur = prod.id
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

        idProdCur?.let { id ->
            viewModel.addProductToCart(id)
            showToastOnce(getString(R.string.msg_added_to_cart))
        } ?: run {
            showToastOnce(getString(R.string.msg_wrong))
        }
    }

    private fun onEventBuyNow() {
        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            startActivity(Intent(this, SignInActivity::class.java))
            return
        }
    }

    private fun onFavoriteEvent() {
        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            startActivity(Intent(this, SignInActivity::class.java))
            return
        }

        if (binding.icFavorite.isActivated) viewModel.removeFavoriteByIdProduct(idProdCur ?: "")
        else viewModel.addFavoriteByIdProduct(idProdCur ?: "")
    }
}