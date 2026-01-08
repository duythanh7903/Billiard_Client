package com.datn.bia.a.common

import com.datn.bia.a.domain.model.domain.Cart
import com.datn.bia.a.domain.model.dto.req.ReqProdCheckOut
import com.datn.bia.a.domain.model.dto.res.ResCatDTO
import com.datn.bia.a.domain.model.dto.res.ResCatProductDTO
import com.datn.bia.a.domain.model.dto.res.ResProductDataDTO
import com.datn.bia.a.domain.model.entity.CartEntity
import com.datn.bia.a.domain.model.entity.CategoryEntity
import com.datn.bia.a.domain.model.entity.ProductEntity

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

fun ResProductDataDTO.toProductEntity() = ProductEntity(
    id = this.id ?: "",
    name = this.name ?: "",
    price = this.price ?: 0,
    imageUrl = this.imageUrl ?: "",
    albumImage = this.albumImage ?: emptyList(),
    des = this.des ?: "",
    status = this.status ?: false,
    createdAt = this.createdAt ?: "",
    updatedAt = this.updatedAt ?: "",
    discount = this.discount ?: 0,

    idCateGory = this.category?.id ?: "",
    categoryName = this.category?.name ?: ""
)

fun ProductEntity.toResProductDataDTO() = ResProductDataDTO(
    id = this.id,
    name = this.name,
    price = this.price,
    imageUrl = this.imageUrl,
    albumImage = this.albumImage,
    des = this.des,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    discount = this.discount,
    category = ResCatProductDTO(
        id = this.idCateGory,
        name = this.categoryName
    )
)

fun ResCatDTO.toCategoryEntity() =
    CategoryEntity(
        id = this.id ?: "",
        name = this.name ?: "",
        isActive = this.isActive ?: false,
        v = this.v ?: 0
    )

fun CategoryEntity.toResCatDTO() =
    ResCatDTO(
        id = this.id,
        name = this.name,
        isActive = this.isActive,
        v = this.v
    )

fun List<CategoryEntity>.toListResCatDTO() = this.map { it.toResCatDTO() }

fun List<ResCatDTO>.toListCategoryEntities() = this.map { it.toCategoryEntity() }

fun List<ProductEntity>.toListProductDataDTO() = this.map { it.toResProductDataDTO() }

fun List<ResProductDataDTO>.toListCacheProduct() = this.map { it.toProductEntity() }

fun List<CartEntity>.toListCart(listProduct: List<ResProductDataDTO>) =
    this.map { it.toCart(listProduct) }

fun Cart.toReqProdCheckOut() = ReqProdCheckOut(
    productId = this.productId,
    quantity = this.productQuantity,
    name = this.productName,
    priceBeforeDis = this.productPrice,
    priceAfterDis = this.productPrice - (this.productPrice * this.productDiscount / 100)
)

fun List<Cart>.toListReqProdCheckOut() = this.map { it.toReqProdCheckOut() }