package com.campaign.controller.product

import com.campaign.controller.product.model.response.MinPriceProductInCategory
import com.campaign.controller.product.model.response.MinPriceProductTableResponse
import com.campaign.controller.product.model.response.MinTotalPriceByBrandResponse
import com.campaign.service.MinPriceProductProvider
import com.campaign.service.MinTotalPriceBrandProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val minPriceProductProvider: MinPriceProductProvider,
    private val minTotalPriceBrandProvider: MinTotalPriceBrandProvider,
) {
    @GetMapping("/min-price/all-categories/by-price")
    fun getMinPriceByCategory(): MinPriceProductTableResponse {
        val table = minPriceProductProvider.productTableForAllCategories()

        return MinPriceProductTableResponse(
            products = table.products,
            totalPrice = table.totalPrice,
        )
    }

    @GetMapping("/min-total-price/all-categories/by-brand")
    fun getMinTotalPriceInAllCategoriesByBrand(): MinTotalPriceByBrandResponse {
        return minTotalPriceBrandProvider.productTableForAllCategories()?.let {
            MinTotalPriceByBrandResponse(
                brandName = it.brandName,
                categories = it.products.map { product ->
                    MinPriceProductInCategory(
                        category = product.category.categoryName,
                        price = product.price,
                    )
                },
                totalPrice = it.totalPrice,
            )
        }
            ?: throw RuntimeException()
    }
}
