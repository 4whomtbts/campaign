package com.campaign.service

import com.campaign.domain.product.Product
import com.campaign.domain.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductPriceProvider(
    private val productRepository: ProductRepository,
) {
    fun queryMinAndMaxPriceInCategory(category: Product.ProductCategory): MinAndMaxProductInCategory {
        return MinAndMaxProductInCategory(
            category = category,
            minProduct = productRepository.selectMinPriceProductInCategory(category),
            maxProduct = productRepository.selectMaxPriceProductInCategory(category),
        )
    }

    data class MinAndMaxProductInCategory(
        val category: Product.ProductCategory,
        val minProduct: Product?,
        val maxProduct: Product?,
    )
}
