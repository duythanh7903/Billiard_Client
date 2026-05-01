package com.datn.vpp.sp26.present.user.activity.order.history.tab

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.base.BaseFragment
import com.datn.vpp.sp26.common.base.ext.showToastOnce
import com.datn.vpp.sp26.common.toListResOrderDTO
import com.datn.vpp.sp26.databinding.FragmentTabOrderBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.present.user.activity.order.history.OrderViewModel
import com.datn.vpp.sp26.present.user.dialog.CommentDialog
import com.datn.vpp.sp26.present.user.dialog.ConfirmCancelOrderDialog
import com.datn.vpp.sp26.present.user.dialog.ConfirmCompleteDialog
import com.datn.vpp.sp26.present.user.dialog.LoadingDialog
import com.datn.vpp.sp26.present.user.dialog.ReasonCancelDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class TabOrderFragment : BaseFragment<FragmentTabOrderBinding>() {

    private val viewModel: OrderViewModel by activityViewModels()

    private var orderAdapter: OrderAdapter? = null
    private var loadingDialog: LoadingDialog? = null
    private var commentDialog: CommentDialog? = null
    private var confirmCancelOrderDialog: ConfirmCancelOrderDialog? = null
    private var confirmCompleteDialog: ConfirmCompleteDialog? = null
    private var reasonCancelDialog: ReasonCancelDialog? = null

    private var resOrder: ResOrderDTO? = null

    private var index: Int = 0

    override fun inflateBinding(): FragmentTabOrderBinding =
        FragmentTabOrderBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        index = arguments?.getInt(AppConst.KEY_ORDER_TYPE) ?: 0

        loadingDialog = LoadingDialog(requireContext())

        commentDialog = CommentDialog(
            contextParams = requireContext(),
            onLater = {

            }, onRate = { stars, comment, orderId ->
                viewModel.postComment(
                    orderId = resOrder?._id ?: "",
                    productIds = resOrder?.products?.map { it.productId?._id ?: "" } ?: emptyList(),
                    stars = stars,
                    comment = comment
                )
            }
        )

        reasonCancelDialog = ReasonCancelDialog(
            contextParams = requireContext(),
            onMessage = { message ->
                if (message.isEmpty()) {
                    requireContext().showToastOnce(getString(R.string.msg_input_null))
                    return@ReasonCancelDialog
                }

                viewModel.cancelOrderUseCase(
                    resOrder?._id ?: "",
                    AppConst.STATUS_ORDER_TO_CANCELLED,
                    message
                )
            }
        )

        confirmCancelOrderDialog = ConfirmCancelOrderDialog(
            requireContext(),
            {

            }, {
                reasonCancelDialog?.show()
            }
        )

        confirmCompleteDialog = ConfirmCompleteDialog(
            requireContext(),
            {

            }, {
                viewModel.updateOrderUseCase(cacheId, AppConst.STATUS_ORDER_TO_COMPLETED)
                requireContext().showToastOnce(getString(R.string.msg_confirm_success))
            }
        )

        binding.rcvOrder.apply {
            orderAdapter = OrderAdapter(
                contextParams = requireContext(),
                status = when (index) {
                    0 -> AppConst.STATUS_ORDER_TO_PAY
                    1 -> AppConst.STATUS_ORDER_TO_RECEIVE
                    2 -> AppConst.STATUS_ORDER_TO_COMPLETED
                    else -> AppConst.STATUS_ORDER_TO_CANCELLED
                },
                onClickItem = { index, res ->
                    showInvoiceDialog(res)
                }, onCancel = { index, res ->
                    resOrder = res
                    confirmCancelOrderDialog?.show()
                }, onReview = { index, res ->
                    resOrder = res
                    commentDialog?.showDialog(res._id ?: "")
                }, onConfirm = { index, res ->
                    cacheId = res._id ?: ""
                    confirmCompleteDialog?.show()
                }
            )

            adapter = orderAdapter
        }
    }

    private var cacheId: String = ""

    private fun showInvoiceDialog(order: ResOrderDTO) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_invoice, null, false)

        val tvOrderInfo = view.findViewById<TextView>(R.id.tvInvoiceOrderInfo)
        val tvCustomerInfo = view.findViewById<TextView>(R.id.tvInvoiceCustomerInfo)
        val tvProducts = view.findViewById<TextView>(R.id.tvInvoiceProducts)
        val tvTotal = view.findViewById<TextView>(R.id.tvInvoiceTotal)

        val orderCode = order.madh?.toString()?.let { "DH$it" } ?: (order._id ?: "--")
        val date = order.orderDate ?: order.createdAt ?: "--"
        val status = order.status ?: "--"
        val payment = order.payment ?: "--"

        tvOrderInfo.text = buildString {
            append("Mã đơn: ").append(orderCode).append('\n')
            append("Ngày: ").append(date).append('\n')
            append("Trạng thái: ").append(status).append('\n')
            append("Thanh toán: ").append(payment)
        }

        val note = order.note?.takeIf { it.isNotBlank() }
        tvCustomerInfo.text = buildString {
            append("Khách hàng: ").append(order.customerName ?: "--").append('\n')
            append("SĐT: ").append(order.phone ?: "--").append('\n')
            append("Địa chỉ: ").append(order.address ?: "--")
            if (note != null) {
                append('\n')
                append("Ghi chú: ").append(note)
            }
        }

        val currency = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        val productsText = order.products.orEmpty().mapIndexed { i, p ->
            val name = p.name ?: p.productId?.name ?: "Sản phẩm"
            val qty = p.quantity ?: 0
            val unit = p.priceAfterDis ?: p.priceBeforeDis ?: p.productId?.price ?: 0.0
            val lineTotal = unit * qty
            val color = p.color?.takeIf { it.isNotBlank() }

            buildString {
                append(i + 1).append(". ").append(name)
                if (color != null) append(" (").append(color).append(")")
                append('\n')
                append("   SL: ").append(qty)
                append("  |  Đơn giá: ").append(currency.format(unit))
                append("  |  T.tiền: ").append(currency.format(lineTotal))
            }
        }.joinToString("\n\n")

        tvProducts.text = if (productsText.isBlank()) "—" else productsText

        tvTotal.text = "TỔNG THANH TOÁN: ${currency.format(order.totalPrice ?: 0.0)}"

        AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Đóng") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun observeData() {
        super.observeData()

        lifecycleScope.launch {
            viewModel.listOrder.collect { listOrder ->
                val data = listOrder.filter {
                    when (index) {
                        0 -> it.status == AppConst.STATUS_ORDER_TO_PAY
                        1 -> it.status == AppConst.STATUS_ORDER_TO_RECEIVE
                        2 -> it.status == AppConst.STATUS_ORDER_TO_COMPLETED
                        else -> it.status == AppConst.STATUS_ORDER_TO_CANCELLED
                    }
                }
                orderAdapter?.comments = viewModel.state.value.listCommentCaches.toMutableList()
                orderAdapter?.submitData(data.toListResOrderDTO())
            }
        }

        lifecycleScope.launch {
            viewModel.uiStateUpdate.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> {
                        loadingDialog?.cancel()
                        requireContext().showToastOnce(getString(R.string.msg_wrong))
                        viewModel.changeStateUpdateToIdle()
                    }

                    UiState.Idle -> {}
                    UiState.Loading -> {
                        loadingDialog?.cancel()
                    }

                    is UiState.Success -> {
                        loadingDialog?.cancel()

                        val response = uiState.data
                        val listOrderTemp = viewModel.listOrder.first().toListResOrderDTO()
                        val indexResponse = listOrderTemp.indexOfFirst { it._id == response._id }

                        if (indexResponse != -1) {
                            val newList = listOrderTemp.toMutableList()
                            newList[indexResponse] = newList[indexResponse].copy(
                                status = response.status
                            )
                            viewModel.changeStateUpdateToIdle()

                            return@collect
                        }

//                        requireContext().showToastOnce(getString(R.string.msg_wrong))
                        viewModel.changeStateUpdateToIdle()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.stateComment.collect { uiState ->
                when (uiState) {
                    is UiState.Error -> {
                        loadingDialog?.cancel()
                        commentDialog?.cancel()

                        viewModel.resetStateComment()
                    }

                    UiState.Idle -> {
                        commentDialog?.cancel()
                        loadingDialog?.cancel()
                    }

                    UiState.Loading -> {
                        commentDialog?.cancel()
                        loadingDialog?.show()
                    }

                    is UiState.Success<*> -> {
                        loadingDialog?.cancel()
                        commentDialog?.cancel()
                        requireContext().showToastOnce(getString(R.string.thanks_for_rating))

                        viewModel.resetStateComment()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        loadingDialog?.cancel()
        loadingDialog = null
        commentDialog?.cancel()
        commentDialog = null
        confirmCancelOrderDialog?.cancel()
        confirmCancelOrderDialog = null
        reasonCancelDialog?.cancel()
        reasonCancelDialog = null
        confirmCompleteDialog?.dismiss()
        confirmCompleteDialog = null

        super.onDestroyView()
    }
}