package com.datn.bia.a.present.fragment.cart

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.formatVND
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.databinding.ItemCartBinding
import com.datn.bia.a.domain.model.domain.Cart

class CartAdapter(
    private val contextParams: Context,
    private val onIncreaseProduct: (idCart: Long) -> Unit,
    private val onReduceProduct: (idCart: Long) -> Unit,
    private val onChangeQuantityProduct: (str: String) -> Unit,
    private val onSelectCart: (cart: Cart, index: Int) -> Unit
) : BaseRecyclerViewAdapter<Cart>() {

    var listCartSelected = mutableListOf<Long>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemLayout(): Int = R.layout.item_cart

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<Cart>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(
        binding: ViewDataBinding,
        item: Cart,
        layoutPosition: Int
    ) {
        if (binding is ItemCartBinding) {
            Glide.with(contextParams).load(item.productImage).into(binding.imgProduct)
            binding.tvProductName.text = item.productName
            binding.edtQuantity.setText(item.productQuantity.toString())

            if (item.productDiscount != 0) {
                binding.tvDiscount.apply {
                    text = "-${item.productDiscount}%"
                    visibleView()
                }
                binding.tvPrice1.apply {
                    text = item.productPrice.formatVND()
                    visibleView()
                }
                binding.lineTvPrice1.visibleView()
                binding.tvPrice.text =
                    (item.productPrice - (item.productPrice * item.productDiscount / 100)).formatVND()
            } else {
                binding.tvDiscount.goneView()
                binding.tvPrice1.goneView()
                binding.lineTvPrice1.goneView()
                binding.tvPrice.text = item.productPrice.formatVND()
            }

            binding.chb.isActivated =
                listCartSelected.firstOrNull { it == item.cartId } != null
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: Cart, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemCartBinding) {

            binding.icPlus.click {
                onIncreaseProduct.invoke(obj.cartId)
            }

            binding.icMinus.click {
                onReduceProduct.invoke(obj.cartId)
            }

            binding.edtQuantity.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = Unit

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
                ) = onChangeQuantityProduct.invoke(s?.toString() ?: "")
            })

            binding.chb.click {
                onSelectCart.invoke(obj, layoutPosition)
            }
        }
    }
}