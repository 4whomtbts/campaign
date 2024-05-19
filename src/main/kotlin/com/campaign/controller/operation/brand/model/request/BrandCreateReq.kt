package com.campaign.controller.operation.brand.model.request

import com.campaign.controller.DefaultResponse

data class BrandCreateReq(
    val brands: List<BrandDetail>,
) : DefaultResponse()

data class BrandDetail(
    val brandName: String,
) : DefaultResponse()
