package com.campaign.service

import com.campaign.domain.product.MinPriceProductInAllCategoryByBrand
import com.campaign.domain.product.Product
import com.campaign.domain.product.ProductRepository
import com.campaign.service.model.BrandGroupKey
import com.campaign.service.model.MinAndMaxProductInCategory
import com.campaign.service.model.MinPricePurchaseInSingleBrand
import com.campaign.service.model.MinPurchasePriceForAllCategory
import com.campaign.service.model.ProductDetail
import org.springframework.stereotype.Service

@Service
class ProductPriceProvider(
    private val productRepository: ProductRepository,
) {
    fun queryMinPriceToPurchaseAllCategory(): MinPurchasePriceForAllCategory {
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
        return MinPurchasePriceForAllCategory(
            products = productDetails,
        )
    }

    fun queryMinPurchasePriceForAllCategoryInSingleBrand(): MinPricePurchaseInSingleBrand? {
        val productsByBrand =
            productRepository.selectMinPriceProductInAllCategoryByBrand()

        if (productsByBrand.isEmpty()) {
            return null
        }

        val mapByBrand =
            productsByBrand.groupBy { BrandGroupKey(it.brandId, it.brandName) }
        val result =
            mapByBrand.minByOrNull { it.value.sumOf(MinPriceProductInAllCategoryByBrand::price) } ?: return null

        return MinPricePurchaseInSingleBrand(
            brandName = result.key.brandName,
            products = result.value.map {
                ProductDetail(
                    category = it.category,
                    brandName = it.brandName,
                    price = it.price,
                )
            },
        )
    }

    fun queryMinAndMaxPriceInCategory(category: Product.ProductCategory): MinAndMaxProductInCategory {
        return MinAndMaxProductInCategory(
            category = category,
            minProduct = productRepository.selectMinPriceProductInCategory(category),
            maxProduct = productRepository.selectMaxPriceProductInCategory(category),
        )
    }
}
