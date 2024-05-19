package com.campaign.controller.operation.brand.model.request

data class BrandCreateReq(
    val brands: List<BrandDetail>,
)

data class BrandDetail(
    val brandName: String,
)
