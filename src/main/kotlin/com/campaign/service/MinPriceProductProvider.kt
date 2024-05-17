package com.campaign.service

import com.campaign.domain.product.Product
import com.campaign.domain.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class MinPriceProductProvider(
    private val productRepository: ProductRepository,
) {
    fun productTableForAllCategories(): MinPriceProductTable {
        val productDetails = productRepository.minPriceProductInAllCategories()
            .groupBy(Product::category)
            .map { entry ->
                /** TODO
                 * 최저가의 동일한 카테고리 상품이 복수개 있는 경우
                 * 브랜드의 이름이 사전순서상 앞에 있는 상품을 선택한다
                 * 추후에 관련된 비즈니스 요구사항이 구체화 되면
                 * 해당 요구사항으로 재구현 필요하다.
                 */
                ProductDetail.of(entry.value.maxBy { it.brand.brandName })
            }
        return MinPriceProductTable(
            products = productDetails,
        )
    }

    data class MinPriceProductTable(
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
            fun of(product: Product): ProductDetail {
                return ProductDetail(
                    category = product.category,
                    brandName = product.brand.brandName,
                    price = product.price,
                )
            }
        }
    }
}
