package com.campaign.controller.operation.brand.model.request

data class BrandUpdateReq(
    val brands: List<BrandUpdate>,
)

data class BrandUpdate(
    val brandId: Long,
    val updatedBrandName: String?,
)
