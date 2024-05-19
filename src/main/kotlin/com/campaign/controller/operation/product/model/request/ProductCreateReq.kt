package com.campaign.controller.operation.product.model.request

data class ProductCreateReq(
    val products: List<ProductCreate>,
)

data class ProductCreate(
    val brandId: Long,
    val productCategoryCode: Int,
    val price: Long,
)
