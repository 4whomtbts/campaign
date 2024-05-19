package com.campaign.service.model

import com.campaign.domain.product.Product

data class ProductDetail(
    val category: Product.ProductCategory,
    val brandName: String,
    val price: Long,
) {
    companion object {
        fun of(product: Product): ProductDetail {
            return ProductDetail(
                category = product.category,
                brandName = product.brand.brandName,
                price = product.price,
            )
        }
    }
}
