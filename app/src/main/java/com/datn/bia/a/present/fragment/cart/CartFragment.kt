package com.datn.bia.a.present.fragment.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.MethodPayment
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.base.BaseFragment
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.formatVND
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.showToastOnce
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.common.toListCart
import com.datn.bia.a.common.toListProductDataDTO
import com.datn.bia.a.common.toListReqProdCheckOut
import com.datn.bia.a.data.storage.SharedPrefCommon
import com.datn.bia.a.databinding.FragmentCartBinding
import com.datn.bia.a.domain.model.domain.Cart
import com.datn.bia.a.domain.model.dto.req.ReqProdCheckOut
import com.datn.bia.a.domain.model.dto.res.ResLoginUserDTO
import com.datn.bia.a.domain.model.dto.res.ResProductDTO
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO
import com.datn.bia.a.domain.model.entity.CartEntity
import com.datn.bia.a.present.activity.auth.si.SignInActivity
import com.datn.bia.a.present.activity.order.confirm.ConfirmOrderActivity
import com.datn.bia.a.present.activity.order.history.OrderActivity
import com.datn.bia.a.present.activity.setting.SettingActivity
import com.datn.bia.a.present.activity.voucher.VouchersActivity
import com.datn.bia.a.present.dialog.LoadingDialog
import com.datn.bia.a.present.dialog.MethodDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val viewModel: CartViewModel by viewModels()

    private var cartAdapter: CartAdapter? = null
    private var resProdDto: ResProductDTO? = null
    private var methodDialog: MethodDialog? = null
    private var loadingDialog: LoadingDialog? = null
    private var gson: Gson? = null

    private var totalPriceCache = 0
    private var listProduct = mutableListOf<ReqProdCheckOut>()

    override fun inflateBinding(): FragmentCartBinding =
        FragmentCartBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        gson = Gson()
        binding.rcvCart.apply {
            cartAdapter = CartAdapter(
                contextParams = requireContext(),
                onIncreaseProduct = { idCart ->
                    viewModel.inCreaseCart(idCart)
                }, onReduceProduct = { idCart ->
                    viewModel.reduceCart(idCart)
                }, onChangeQuantityProduct = { str ->

                }, onSelectCart = { cart, index ->
                    viewModel.selectCart(cart.cartId)
                }
            )

            adapter = cartAdapter
        }

        methodDialog = MethodDialog(
            context = requireContext(),
            onConfirmMethodPayment = { method ->
                when (method) {
                    MethodPayment.CASH_ON_DELIVERY -> {
                        moveToConfirmActivity(MethodPayment.CASH_ON_DELIVERY.name)
                    }

                    MethodPayment.GOOGLE_PAY -> {
                        moveToConfirmActivity(MethodPayment.GOOGLE_PAY.name)
                    }

                    MethodPayment.ZALO_PAY -> {
                        moveToConfirmActivity(MethodPayment.ZALO_PAY.name)
                    }

                    MethodPayment.VN_PAY -> {
                        moveToConfirmActivity(MethodPayment.VN_PAY.name)
                    }
                }
            }
        )

        loadingDialog = LoadingDialog(requireContext())
    }

    @SuppressLint("SetTextI18n")
    override fun observeData() {
        super.observeData()

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
                    return@collect
                }

                requireContext().showToastOnce(getString(R.string.msg_wrong))
                viewModel.resetFlagIncreaseCart()
            }
        }

        lifecycleScope.launch {
            viewModel.flagReduceCart.collect { isSuccess ->
                if (isSuccess == null) return@collect

                if (isSuccess) {
                    return@collect
                }

                requireContext().showToastOnce(getString(R.string.msg_wrong))
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
                        val totalPrice =
                            listCartSelected.sumOf { (it.productPrice - (it.productPrice * it.productDiscount / 100)) * it.productQuantity }
                        val voucher = viewModel.voucherSelected.first()
                        var priceAfterAddVoucher = if (voucher == null) totalPrice else {
                            totalPrice - (totalPrice * (voucher.discount ?: 0) / 100)
                        }
                        val priceDiscount = totalPrice - priceAfterAddVoucher
                        priceAfterAddVoucher = if (priceDiscount > (voucher?.maxPriceDis ?: 0)) totalPrice - (voucher?.maxPriceDis ?: 0)
                        else priceAfterAddVoucher

                        listProduct.apply {
                            clear()
                            addAll(listCartSelected.toListReqProdCheckOut())
                        }

                        totalPriceCache = priceAfterAddVoucher

                        text = priceAfterAddVoucher.formatVND()
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
                    priceAfterAddVoucher = if (priceDiscount > (voucher?.maxPriceDis ?: 0)) totalPrice - (voucher?.maxPriceDis ?: 0)
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
                        requireContext().showToastOnce(uiState.message)

                        viewModel.resetStateCheckOutToIdle()
                    }

                    UiState.Idle -> {}
                    UiState.Loading -> {
                        loadingDialog?.show()
                    }

                    is UiState.Success -> {
                        loadingDialog?.cancel()
                        startActivity(Intent(requireContext(), OrderActivity::class.java))

                        viewModel.resetStateCheckOutToIdle()
                    }
                }
            }
        }
    }

    override fun clickViews() {
        super.clickViews()

        binding.icChb.click { onSelectAllCartEvent() }

        binding.btnCheckOut.click { onCheckOutEvent() }

        binding.layoutVoucher.click { onVoucherEvent() }

        binding.icChat.click { onChatEvent() }
    }

    override fun onDestroyView() {
        cartAdapter?.list?.clear()
        cartAdapter = null
        resProdDto = null
        methodDialog?.cancel()
        methodDialog = null
        loadingDialog?.cancel()
        loadingDialog = null
        gson = null

        super.onDestroyView()
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 999) {
            val json = data?.getStringExtra(AppConst.KEY_VOUCHER) ?: ""
            Gson().fromJson(json, ResVoucherDTO::class.java)?.let { voucher ->
                viewModel.changeVoucher(voucher)
            } ?: run {
                requireContext().showToastOnce(getString(R.string.msg_wrong))
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
            startActivity(Intent(requireContext(), SignInActivity::class.java))
            return
        }

        if (gson?.fromJson(
                SharedPrefCommon.jsonAcc,
                ResLoginUserDTO::class.java
            )?.user?.address == null || gson?.fromJson(
                SharedPrefCommon.jsonAcc, ResLoginUserDTO::class.java
            )?.user?.phone == null
        ) {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
            return
        }

        if (viewModel.listIdCartSelected.value.isEmpty()) return

        methodDialog?.show()
    }

    private fun onVoucherEvent() {
        if (viewModel.listIdCartSelected.value.isEmpty()) return

        startActivityForResult(
            Intent(requireContext(), VouchersActivity::class.java),
            999
        )
    }

    private fun onChatEvent() {
        if (SharedPrefCommon.jsonAcc.isEmpty()) {
            startActivity(Intent(requireContext(), SignInActivity::class.java))
            return
        }
    }

    private fun moveToConfirmActivity(
        type: String
    ) = startActivity(
        Intent(
            requireContext(),
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
        }
    )
}