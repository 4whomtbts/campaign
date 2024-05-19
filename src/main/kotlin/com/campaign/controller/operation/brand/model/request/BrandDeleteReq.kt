package com.campaign.controller.operation.brand.model.request

import com.campaign.controller.DefaultResponse

data class BrandDeleteReq(
    val targets: List<BrandDelete>,
) : DefaultResponse()

data class BrandDelete(
    val brandId: Long,
) : DefaultResponse()
