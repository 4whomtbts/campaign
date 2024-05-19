package com.campaign.controller.product.model.response

data class MinAndMaxProductResponse(
    val category: String,
    val minPrice: List<SimpleProduct>,
    val maxPrice: List<SimpleProduct>,
)
