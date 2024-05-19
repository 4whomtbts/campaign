package com.campaign.service.model

import com.campaign.domain.product.Product

data class MinPurchasePriceForAllCategory(
    val products: List<ProductDetail>,
) {
    val totalPrice = products.sumOf(ProductDetail::price)
}

data class MinAndMaxProductInCategory(
    val category: Product.ProductCategory,
    val minProduct: Product?,
    val maxProduct: Product?,
)
