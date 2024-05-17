package com.campaign.service

import com.campaign.domain.product.MinPriceProductInAllCategoryByBrand
import com.campaign.domain.product.Product
import com.campaign.domain.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class MinTotalPriceBrandProvider(
    private val productRepository: ProductRepository,
) {
    fun productTableForAllCategories(): MinPriceProductTable? {
        val productsByBrand =
            productRepository.selectMinPriceProductInAllCategoriesByBrand()

        if (productsByBrand.isEmpty()) {
            return null
        }

        val mapByBrand =
            productsByBrand.groupBy { BrandGroupKey(it.brandId, it.brandName) }
        val result =
            mapByBrand.minByOrNull { it.value.sumOf(MinPriceProductInAllCategoryByBrand::price) } ?: return null

        return MinPriceProductTable(
            brandName = result.key.brandName,
            products = result.value.map(ProductDetail::of),
        )
    }

    data class MinPriceProductTable(
        val brandName: String,
        val products: List<ProductDetail>,
    ) {
        val totalPrice = products.sumOf(ProductDetail::price)
    }

    data class ProductDetail(
        val category: Product.ProductCategory,
        val brandName: String,
        val price: Long,
    ) {
        companion object {
            fun of(product: MinPriceProductInAllCategoryByBrand): ProductDetail {
                return ProductDetail(
                    category = product.category,
                    brandName = product.brandName,
                    price = product.price,
                )
            }
        }
    }

    data class BrandGroupKey(
        val brandId: Long,
        val brandName: String,
    )
}
