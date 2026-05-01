package com.datn.vpp.sp26.present.wholesale.cart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.text.InputType
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.application.GlobalApp
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.MethodPayment
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.formatVND
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.common.toListCart
import com.datn.vpp.sp26.common.toListProductDataDTO
import com.datn.vpp.sp26.common.toListReqProdCheckOut
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.databinding.ActivityCartBinding
import com.datn.vpp.sp26.domain.model.domain.Cart
import com.datn.vpp.sp26.domain.model.dto.req.ReqProdCheckOut
import com.datn.vpp.sp26.domain.model.dto.res.ResLoginUserDTO
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDTO
import com.datn.vpp.sp26.domain.model.entity.CartEntity
import com.datn.vpp.sp26.present.user.activity.auth.si.SignInActivity
import com.datn.vpp.sp26.present.user.activity.order.confirm.ConfirmOrderActivity
import com.datn.vpp.sp26.present.user.activity.order.history.OrderActivity
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import com.datn.vpp.sp26.present.user.dialog.MethodDialog
import com.datn.vpp.sp26.present.user.fragment.cart.CartAdapter
import com.datn.vpp.sp26.present.user.fragment.cart.CartViewModel
import com.datn.vpp.sp26.present.wholesale.profile.ProfileActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {

    private val viewModel: CartViewModel by viewModels()

    private var cartAdapter: CartAdapter? = null
    private var resProdDto: ResProductDTO? = null
    private var methodDialog: MethodDialog? = null
    private var loadingDialog: LoadingDialog? = null
    private var gson: Gson? = null

    private var totalPriceCache = 0.0
    private var listProduct = mutableListOf<ReqProdCheckOut>()

    override fun getLayoutActivity(): Int = R.layout.activity_cart

    override fun initViews() {
        super.initViews()

        gson = Gson()
        binding.rcvCart.apply {
            cartAdapter = CartAdapter(
                contextParams = this@CartActivity,
                onIncreaseProduct = { idCart ->
                    viewModel.inCreaseCart(idCart)
                }, onReduceProduct = { idCart ->
                    viewModel.reduceCart(idCart)
                }, onChangeQuantityProduct = { id ->
                    showChangeQuantityDialog(id)
                }, onSelectCart = { cart, index ->
                    viewModel.selectCart(cart.cartId)
                }
            )

            adapter = cartAdapter
        }

        methodDialog = MethodDialog(
            context = this@CartActivity,
            onConfirmMethodPayment = { method ->
                when (method) {
                    MethodPayment.CASH_ON_DELIVERY -> {
                    }

                    MethodPayment.GOOGLE_PAY -> {
                    }

                    MethodPayment.ZALO_PAY -> {
                    }

                    MethodPayment.VN_PAY -> {
                    }
                }
            }
        )

        loadingDialog = LoadingDialog(this@CartActivity)
    }

    override fun onResume() {
        super.onResume()

        if (GlobalApp.isPaymentSuccess) {
            GlobalApp.isPaymentSuccess = false
            finish()
        }
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icBack.click {
            finish()
        }

        binding.icChb.click { onSelectAllCartEvent() }

        binding.btnCheckOut.click {
            onCheckOutEvent()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun observerData() {
        super.observerData()

        lifecycleScope.launch {
            viewModel.state.collect { cartState ->
                binding.loadingView.goneView()
                binding.rcvCart.visibleView()

                resProdDto = ResProductDTO(
                    pagination = null,
                    data = cartState.listProductEntity.toListProductDataDTO()
                )
                onCartChange(resProdDto, cartState.listCarts)

                binding.tvCountItemInCart.text = "(${cartState.listCarts.sumOf { it.quantity }})"
            }
        }

        lifecycleScope.launch {
            viewModel.flagIncreaseCart.collect { isSuccess ->
                if (isSuccess == null) return@collect

                if (isSuccess) {
                    viewModel.unSelectAllCart()
                    return@collect
                }

                showToastOnce(getString(R.string.msg_wrong))
                viewModel.resetFlagIncreaseCart()
            }
        }

        lifecycleScope.launch {
            viewModel.flagReduceCart.collect { isSuccess ->
                if (isSuccess == null) return@collect

                if (isSuccess) {
                    viewModel.unSelectAllCart()
                    return@collect
                }

                showToastOnce(getString(R.string.msg_wrong))
                viewModel.resetFlagReduceCart()
            }
        }

        lifecycleScope.launch {
            viewModel.listIdCartSelected.collect { listId ->
                cartAdapter?.listCartSelected = listId

                binding.icChb.isActivated =
                    listId.size == (cartAdapter?.list?.size ?: 0) && listId.isNotEmpty()

                val listCartSelected = cartAdapter?.list?.filter { cart ->
                    listId.any { id -> cart.cartId == id }
                } ?: emptyList()

                binding.btnCheckOut.text =
                    "${getString(R.string.check_out)} (${listCartSelected.sumOf { it.productQuantity }})"

                if (listId.isEmpty()) {
                    binding.icTruck.goneView()
                    binding.tvPriceShip.goneView()
                    binding.tvPrice.goneView()
                    binding.viewLine.goneView()
                } else {
                    binding.icTruck.visibleView()
                    binding.tvPriceShip.apply {
                        visibleView()
                    }
                    binding.tvPrice.apply {
                        totalPriceCache =
                            listCartSelected.sumOf { (it.productPrice) * it.productQuantity }
                        listProduct.apply {
                            clear()
                            addAll(listCartSelected.toListReqProdCheckOut())
                        }

                        text = totalPriceCache.formatVND()
                        visibleView()
                    }
                    binding.viewLine.visibleView()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.voucherSelected.collect { voucher ->
                binding.tvDiscount.text = if (voucher != null) "-${voucher.discount}%" else ""

                binding.tvPrice.apply {
                    val listCartSelected = cartAdapter?.list?.filter { cart ->
                        viewModel.listIdCartSelected.first().any { id -> cart.cartId == id }
                    } ?: emptyList()

                    listProduct.apply {
                        clear()
                        addAll(listCartSelected.toListReqProdCheckOut())
                    }

                    val totalPrice =
                        listCartSelected.sumOf { (it.productPrice - (it.productPrice * it.productDiscount / 100)) * it.productQuantity }
                    val voucher = viewModel.voucherSelected.first()
                    var priceAfterAddVoucher = if (voucher == null) totalPrice else {
                        totalPrice - (totalPrice * (voucher.discount ?: 0) / 100)
                    }
                    val priceDiscount = totalPrice - priceAfterAddVoucher
                    priceAfterAddVoucher = if (priceDiscount > (voucher?.maxPriceDis
                            ?: 0)
                    ) totalPrice - (voucher?.maxPriceDis ?: 0)
                    else priceAfterAddVoucher

                    totalPriceCache = priceAfterAddVoucher

                    text = priceAfterAddVoucher.formatVND()
                    visibleView()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.stateCheckOut.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> {
                        loadingDialog?.cancel()
                        showToastOnce(uiState.message)

                        viewModel.resetStateCheckOutToIdle()
                    }

                    UiState.Idle -> {}
                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success -> {
                        loadingDialog?.cancel()
                        startActivity(Intent(this@CartActivity, OrderActivity::class.java))

                        viewModel.resetStateCheckOutToIdle()
                    }
                }
            }
        }
    }

    private fun onCartChange(resProductDTO: ResProductDTO?, listCartEntities: List<CartEntity>) {
        if (resProductDTO == null) return

        if (resProductDTO.data == null) return

        val listCart = listCartEntities.toListCart(resProductDTO.data)
        cartAdapter?.submitData(listCart)
    }

    private fun onSelectAllCartEvent() {
        if (binding.icChb.isActivated) viewModel.unSelectAllCart()
        else viewModel.selectAllCart(cartAdapter?.list?.map { it.cartId } ?: emptyList())
    }

    private fun onCheckOutEvent() {
        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            startActivity(Intent(this, SignInActivity::class.java))
            return
        }

        if (gson?.fromJson(
                SharedPrefCommon.jsonAcc,
                ResLoginUserDTO::class.java
            )?.user?.address == null || gson?.fromJson(
                SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java
            )?.user?.phone == null
        ) {
            startActivity(Intent(this, ProfileActivity::class.java))
            return
        }

        if (viewModel.listIdCartSelected.value.isEmpty()) return

        moveToConfirmActivity(MethodPayment.GOOGLE_PAY.name)
    }

    private fun moveToConfirmActivity(
        type: String
    ) = startActivity(
        Intent(
            this,
            ConfirmOrderActivity::class.java
        ).apply {
            putExtra(AppConst.KEY_PAYMENT_METHOD, type)
            putExtra(
                AppConst.KEY_LIST_CART,
                gson?.toJson(cartAdapter?.getAllCartSelected() ?: emptyList<Cart>())
            )
            putExtra(AppConst.KEY_TOTAL_PRICE, totalPriceCache)
            putExtra(AppConst.KEY_ID_VOUCHER, viewModel.voucherSelected.value?.id)
            putExtra(
                AppConst.KEY_LIST_PRODUCT,
                gson?.toJson(listProduct)
            )

            listProduct.forEach { Log.d("product", it.toString()) }
        }
    )

    private fun showChangeQuantityDialog(idCart: Long) {
        val currentQty = cartAdapter?.list?.firstOrNull { it.cartId == idCart }?.productQuantity ?: 1

        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(currentQty.toString())
            setSelection(text?.length ?: 0)
        }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.quantity))
            .setMessage(getString(R.string.msg_input_quantity))
            .setView(input)
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
                val qty = input.text?.toString()?.trim()?.toIntOrNull()
                if (qty == null || qty <= 0) {
                    showToastOnce(getString(R.string.msg_input_null))
                    return@setPositiveButton
                }
                viewModel.onChangeQuantity(qty, idCart)
                dialog.dismiss()
            }
            .show()
    }
}