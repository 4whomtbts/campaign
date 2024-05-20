package com.campaign.controller.product

import com.campaign.controller.product.model.response.MinAndMaxProductDto
import com.campaign.controller.product.model.response.MinAndMaxProductResponse
import com.campaign.controller.product.model.response.MinPriceProductInCategory
import com.campaign.controller.product.model.response.MinPriceProductTableDto
import com.campaign.controller.product.model.response.MinTotalPriceByBrandDto
import com.campaign.controller.product.model.response.MinTotalPriceForAllCategoryByBrandResponse
import com.campaign.controller.product.model.response.MinTotalPriceForAllCategoryResponse
import com.campaign.controller.product.model.response.SimpleProduct
import com.campaign.domain.product.Product
import com.campaign.service.ProductPriceProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productPriceProvider: ProductPriceProvider,
) {
    /**
     * API: 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
     */
    @GetMapping("/min-total-price/all-categories/by-price")
    fun getMinTotalPriceForAllCategory(): MinTotalPriceForAllCategoryResponse {
        return MinTotalPriceForAllCategoryResponse(
            data = productPriceProvider.queryMinPriceToPurchaseAllCategory().let {
                MinPriceProductTableDto(
                    products = it.products,
                    totalPrice = it.totalPrice,
                )
            },
        )
    }

    /**
     * API: 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
     */
    @GetMapping("/min-total-price/all-categories/by-brand")
    fun getMinTotalPriceInAllCategoryByBrand(): MinTotalPriceForAllCategoryByBrandResponse? {
        return MinTotalPriceForAllCategoryByBrandResponse(
            data = productPriceProvider.queryMinPurchasePriceForAllCategoryInSingleBrand()?.let { table ->
                MinTotalPriceByBrandDto(
                    brandName = table.brandName,
                    categories = table.products.map { product ->
                        MinPriceProductInCategory(
                            category = product.category.categoryKoreanName,
                            price = product.price,
                        )
                    },
                    totalPrice = table.totalPrice,
                )
            },
        )
    }

    /**
     * API: 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
     */
    @GetMapping("/min-max-price/categories")
    fun getMinAndMaxPriceProductInCategory(
        @RequestParam(name = "categoryName") categoryName: String,
    ): MinAndMaxProductResponse {
        return productPriceProvider
            .queryMinAndMaxPriceInCategory(category = Product.ProductCategory.ofKoreanName(categoryName)).let {
                MinAndMaxProductResponse(
                    data = MinAndMaxProductDto(
                        category = categoryName,
                        minPrice =
                        it.minProduct?.let { product -> listOf(SimpleProduct.of(product)) }
                            ?: emptyList(),
                        maxPrice =
                        it.maxProduct?.let { product -> listOf(SimpleProduct.of(product)) }
                            ?: emptyList(),
                    ),
                )
            }
    }
}
