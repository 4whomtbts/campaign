package com.campaign.controller.operation.brand.model.request

data class BrandDeleteReq(
    val targets: List<BrandDelete>,
)

data class BrandDelete(
    val brandId: Long,
)
