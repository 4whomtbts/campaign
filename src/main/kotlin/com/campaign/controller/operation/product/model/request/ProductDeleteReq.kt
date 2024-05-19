package com.campaign.controller.operation.product.model.request

import com.campaign.controller.DefaultResponse

data class ProductDeleteReq(
    val targets: List<ProductDelete>,
) : DefaultResponse()

data class ProductDelete(
    val productId: Long,
) : DefaultResponse()
