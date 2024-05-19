package com.campaign.controller.product.model.response

import com.campaign.controller.DefaultResponse

data class MinAndMaxProductResponse(
    val category: String,
    val minPrice: List<SimpleProduct>,
    val maxPrice: List<SimpleProduct>,
) : DefaultResponse()
