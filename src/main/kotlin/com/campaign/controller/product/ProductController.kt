package com.campaign.controller.product

import com.campaign.controller.product.model.response.MinPriceProductTableResponse
import com.campaign.service.MinPriceProductProvider
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val minPriceProductProvider: MinPriceProductProvider,
) {
    @GetMapping("/categories/min-price")
    fun getMinPriceByCategory(): MinPriceProductTableResponse {
        val table = minPriceProductProvider.productTableForAllCategories()

        return MinPriceProductTableResponse(
            products = table.products,
            totalPrice = table.totalPrice,
        )
    }
}
