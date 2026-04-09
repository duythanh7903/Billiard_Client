package com.datn.vpp.sp26.present.activity.order.history.tab

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.formatVND
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.databinding.ItemOrderBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.datn.vpp.sp26.domain.model.entity.CommentEntity

class OrderAdapter(
    private val contextParams: Context,
    private val status: String,
    private val onClickItem: (Int, ResOrderDTO) -> Unit,
    private val onCancel: (Int, ResOrderDTO) -> Unit,
    private val onReview: (Int, ResOrderDTO) -> Unit,
    private val onConfirm: (Int, ResOrderDTO) -> Unit
) : BaseRecyclerViewAdapter<ResOrderDTO>() {

    var comments = mutableListOf<CommentEntity>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemLayout(): Int = R.layout.item_order

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ResOrderDTO>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(
        binding: ViewDataBinding,
        item: ResOrderDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemOrderBinding) {
            val productAdapter = ProdAdapter(contextParams = contextParams).apply {
                submitData(item.products ?: emptyList())
            }
            val totalItem = item.products?.sumOf { it.quantity ?: 0 } ?: 0

            binding.rcvProductOrder.adapter = productAdapter
            binding.tvTotalCountProduct.text =
                "${contextParams.getString(R.string.total_)} $totalItem ${
                    contextParams.getString(
                        if (totalItem > 1) R.string.items else R.string.item
                    )
                }:"
            binding.tvPriceAll.text = item.totalPrice?.formatVND() ?: "NaN"

            when (status) {
                AppConst.STATUS_ORDER_TO_PAY -> {
                    binding.btnReview.goneView()
                    binding.btnConfirm.goneView()
                    binding.btnCancel.visibleView()
                }

                AppConst.STATUS_ORDER_TO_RECEIVE -> {
                    binding.btnConfirm.visibleView()
                    binding.btnReview.goneView()
                    binding.btnCancel.goneView()
                }

                AppConst.STATUS_ORDER_TO_COMPLETED -> {
                    binding.btnConfirm.goneView()
                    binding.btnReview.visibleView()
                    binding.btnCancel.goneView()

                    if (comments.map { it.orderId }.contains(item._id)) binding.btnReview.goneView()
                }

                else -> {
                    binding.btnConfirm.goneView()
                    binding.btnReview.goneView()
                    binding.btnCancel.goneView()
                }
            }
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ResOrderDTO, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemOrderBinding) {
            binding.layoutNext.click {
                onClickItem.invoke(layoutPosition, obj)
            }

            binding.btnCancel.click {
                onCancel.invoke(layoutPosition, obj)
            }

            binding.btnReview.click {
                onReview.invoke(layoutPosition, obj)
                it?.goneView()
            }

            binding.btnConfirm.click {
                onConfirm.invoke(layoutPosition, obj)
            }
        }
    }
}