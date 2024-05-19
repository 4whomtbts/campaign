package com.campaign.controller.operation.brand.model.request

import com.campaign.controller.DefaultResponse

data class BrandUpdateReq(
    val brands: List<BrandUpdate>,
) : DefaultResponse()

data class BrandUpdate(
    val brandId: Long,
    val updatedBrandName: String?,
) : DefaultResponse()
