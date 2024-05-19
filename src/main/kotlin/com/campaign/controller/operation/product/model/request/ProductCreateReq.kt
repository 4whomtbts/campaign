package com.campaign.controller.operation.product.model.request

import com.campaign.controller.DefaultResponse

data class ProductCreateReq(
    val products: List<ProductCreate>,
) : DefaultResponse()

data class ProductCreate(
    val brandId: Long,
    val productCategoryCode: Int,
    val price: Long,
) : DefaultResponse()
