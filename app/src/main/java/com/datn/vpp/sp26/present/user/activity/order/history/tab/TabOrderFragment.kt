package com.datn.vpp.sp26.present.user.activity.order.history.tab

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
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

        val tvInvoiceNo = view.findViewById<TextView>(R.id.tvInvoiceNo)
        val tvInvoiceDate = view.findViewById<TextView>(R.id.tvInvoiceDate)
        val tvSellerInfo = view.findViewById<TextView>(R.id.tvSellerInfo)
        val tvBuyerInfo = view.findViewById<TextView>(R.id.tvBuyerInfo)
        val tblInvoice = view.findViewById<TableLayout>(R.id.tblInvoice)
        val anchor = view.findViewById<TableRow>(R.id.trInvoiceItemsAnchor)
        val tvTotal = view.findViewById<TextView>(R.id.tvInvoiceTotal)
        val tvStatus = view.findViewById<TextView>(R.id.tvInvoiceStatus)

        val orderCode = order.madh?.toString()?.let { "00000$it" } ?: (order._id ?: "--")
        val date = order.orderDate ?: order.createdAt ?: "--"
        val status = order.status ?: "--"
        val payment = order.payment ?: "--"

        val note = order.note?.takeIf { it.isNotBlank() }
        tvInvoiceNo.text = "Số (invoice no.): $orderCode"
        tvInvoiceDate.text = "Ngày (day): $date"

        tvSellerInfo.text = buildString {
            append("Người bán (Seller): ").append(getString(R.string.app_name)).append('\n')
            append("HTTT (Payment): ").append(payment)
        }

        tvBuyerInfo.text = buildString {
            append("Người mua (Buyer): ").append(order.customerName ?: "--").append('\n')
            append("SĐT (Phone): ").append(order.phone ?: "--").append('\n')
            append("Địa chỉ (Address): ").append(order.address ?: "--")
            if (note != null) {
                append('\n')
                append("Ghi chú (Note): ").append(note)
            }
        }

        val currency = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        val items = order.products.orEmpty()
        val anchorIndex = tblInvoice.indexOfChild(anchor)
        if (anchorIndex != -1) {
            while (tblInvoice.childCount > anchorIndex + 1) {
                tblInvoice.removeViewAt(anchorIndex + 1)
            }
        }

        fun makeCell(
            text: String,
            widthPx: Int? = null,
            gravity: Int,
            bold: Boolean = false
        ): TextView {
            return TextView(requireContext()).apply {
                layoutParams = TableRow.LayoutParams(
                    widthPx ?: ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setPadding(12, 10, 12, 10)
                background = requireContext().getDrawable(R.drawable.bg_invoice_cell)
                this.gravity = gravity
                setTextColor(android.graphics.Color.BLACK)
                textSize = 12f
                if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
                this.text = text
            }
        }

        if (items.isEmpty()) {
            val row = TableRow(requireContext())
            row.addView(makeCell("-", gravity = android.view.Gravity.CENTER))
            row.addView(makeCell("Không có sản phẩm", gravity = android.view.Gravity.START))
            row.addView(makeCell("-", gravity = android.view.Gravity.CENTER))
            row.addView(makeCell("-", gravity = android.view.Gravity.END))
            row.addView(makeCell("-", gravity = android.view.Gravity.END))
            tblInvoice.addView(row)
        } else {
            items.forEachIndexed { i, p ->
                val nameBase = p.name ?: p.productId?.name ?: "Sản phẩm"
                val color = p.color?.takeIf { it.isNotBlank() }
                val name = if (color != null) "$nameBase ($color)" else nameBase
                val qty = p.quantity ?: 0
                val unit = p.priceAfterDis ?: p.priceBeforeDis ?: p.productId?.price ?: 0.0
                val lineTotal = unit * qty

                val row = TableRow(requireContext())
                row.addView(makeCell((i + 1).toString(), gravity = android.view.Gravity.CENTER))
                row.addView(makeCell(name, gravity = android.view.Gravity.START))
                row.addView(makeCell(qty.toString(), gravity = android.view.Gravity.CENTER))
                row.addView(makeCell(currency.format(unit), gravity = android.view.Gravity.END))
                row.addView(makeCell(currency.format(lineTotal), gravity = android.view.Gravity.END))
                tblInvoice.addView(row)
            }
        }

        tvTotal.text = "Tổng cộng tiền thanh toán (Total payment): ${currency.format(order.totalPrice ?: 0.0)}"
        tvStatus.text = "Trạng thái đơn: $status"

        AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton("Đóng") { dialog, _ -> dialog.dismiss() }
            .create()
            .also { dialog ->
                dialog.show()
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
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