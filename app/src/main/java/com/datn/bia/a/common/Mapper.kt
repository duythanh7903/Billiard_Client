package com.datn.bia.a.common

import com.datn.bia.a.domain.model.domain.Cart
import com.datn.bia.a.domain.model.dto.req.ReqProdCheckOut
import com.datn.bia.a.domain.model.dto.res.ResProductDataDTO
import com.datn.bia.a.domain.model.entity.CartEntity

fun CartEntity.toCart(listProduct: List<ResProductDataDTO>): Cart {
    // Tìm product tương ứng với idProd trong CartEntity
    val product = listProduct.firstOrNull { it.id == idProd }

    return Cart(
        cartId = id,
        productId = idProd,
        productQuantity = quantity,
        productPrice = product?.price ?: 0,
        productDiscount = product?.discount ?: 0,
        productImage = product?.imageUrl ?: "",
        productName = product?.name ?: ""
    )
}

fun List<CartEntity>.toListCart(listProduct: List<ResProductDataDTO>) = this.map { it.toCart(listProduct) }

fun Cart.toReqProdCheckOut() = ReqProdCheckOut(
    productId = this.productId,
    quantity = this.productQuantity,
    name = this.productName,
    priceBeforeDis = this.productPrice,
    priceAfterDis = this.productPrice - (this.productPrice * this.productDiscount / 100)
)

fun List<Cart>.toListReqProdCheckOut() = this.map { it.toReqProdCheckOut()}