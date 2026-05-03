package com.datn.vpp.sp26.present.user.activity.order.history

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import java.text.NumberFormat
import java.util.Locale

object InvoiceContentBinder {

    fun bind(context: Context, root: View, order: ResOrderDTO) {
        val tvInvoiceNo = root.findViewById<TextView>(R.id.tvInvoiceNo)
        val tvInvoiceDate = root.findViewById<TextView>(R.id.tvInvoiceDate)
        val tvSellerInfo = root.findViewById<TextView>(R.id.tvSellerInfo)
        val tvBuyerInfo = root.findViewById<TextView>(R.id.tvBuyerInfo)
        val tblInvoice = root.findViewById<TableLayout>(R.id.tblInvoice)
        val anchor = root.findViewById<TableRow>(R.id.trInvoiceItemsAnchor)
        val tvTotal = root.findViewById<TextView>(R.id.tvInvoiceTotal)
        val tvStatus = root.findViewById<TextView>(R.id.tvInvoiceStatus)

        val orderCode = order.madh?.toString()?.let { "00000$it" } ?: (order._id ?: "--")
        val date = order.orderDate ?: order.createdAt ?: "--"
        val status = order.status ?: "--"
        val payment = order.payment ?: "--"

        val note = order.note?.takeIf { it.isNotBlank() }
        tvInvoiceNo.text = "Số (invoice no.): $orderCode"
        tvInvoiceDate.text = "Ngày (day): $date"

        tvSellerInfo.text = buildString {
            append("Người bán (Seller): ").append(context.getString(R.string.app_name)).append('\n')
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
            return TextView(context).apply {
                layoutParams = TableRow.LayoutParams(
                    widthPx ?: ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setPadding(12, 10, 12, 10)
                background = context.getDrawable(R.drawable.bg_invoice_cell)
                this.gravity = gravity
                setTextColor(android.graphics.Color.BLACK)
                textSize = 12f
                if (bold) setTypeface(typeface, android.graphics.Typeface.BOLD)
                this.text = text
            }
        }

        if (items.isEmpty()) {
            val row = TableRow(context)
            row.addView(makeCell("-", gravity = Gravity.CENTER))
            row.addView(makeCell("Không có sản phẩm", gravity = Gravity.START))
            row.addView(makeCell("-", gravity = Gravity.CENTER))
            row.addView(makeCell("-", gravity = Gravity.END))
            row.addView(makeCell("-", gravity = Gravity.END))
            tblInvoice.addView(row)
        } else {
            items.forEachIndexed { i, p ->
                val nameBase = p.name ?: p.productId?.name ?: "Sản phẩm"
                val color = p.color?.takeIf { it.isNotBlank() }
                val name = if (color != null) "$nameBase ($color)" else nameBase
                val qty = p.quantity ?: 0
                val unit = p.priceAfterDis ?: p.priceBeforeDis ?: p.productId?.price ?: 0.0
                val lineTotal = unit * qty

                val row = TableRow(context)
                row.addView(makeCell((i + 1).toString(), gravity = Gravity.CENTER))
                row.addView(makeCell(name, gravity = Gravity.START))
                row.addView(makeCell(qty.toString(), gravity = Gravity.CENTER))
                row.addView(makeCell(currency.format(unit), gravity = Gravity.END))
                row.addView(makeCell(currency.format(lineTotal), gravity = Gravity.END))
                tblInvoice.addView(row)
            }
        }

        tvTotal.text =
            "Tổng cộng tiền thanh toán (Total payment): ${currency.format(order.totalPrice ?: 0.0)}"
        tvStatus.text = "Trạng thái đơn: $status"
    }
}
