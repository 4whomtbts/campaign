package com.campaign.service.model

data class MinPricePurchaseInSingleBrand(
    val brandName: String,
    val products: List<ProductDetail>,
) {
    val totalPrice = products.sumOf(ProductDetail::price)
}

data class BrandGroupKey(
    val brandId: Long,
    val brandName: String,
)
