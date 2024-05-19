package com.campaign.controller.product.model.response

import com.campaign.domain.product.Product

data class SimpleProduct(
    val brandName: String,
    val price: Long,
) {
    companion object {
        fun of(product: Product): SimpleProduct {
            return SimpleProduct(
                brandName = product.brand.brandName,
                price = product.price,
            )
        }
    }
}
