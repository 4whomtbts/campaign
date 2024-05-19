package com.campaign.controller.operation.product

import com.campaign.controller.operation.product.model.request.ProductCreateReq
import com.campaign.controller.operation.product.model.request.ProductDeleteReq
import com.campaign.controller.operation.product.model.request.ProductUpdateReq
import com.campaign.service.operation.product.ProductOperationService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/operation/v1")
class ProductOperationController(
    private val productOperationService: ProductOperationService,
) {
    @PostMapping("/products")
    fun createProducts(
        @RequestBody req: ProductCreateReq,
    ) {
        productOperationService.createProducts(
            req.products.map {
                ProductOperationService.ProductCreate(
                    brandId = it.brandId,
                    productCategoryCode = it.productCategoryCode,
                    price = it.price,
                )
            },
        )
    }

    @PutMapping("/products")
    fun updateProducts(
        @RequestBody req: ProductUpdateReq,
    ) {
        productOperationService.updateProducts(
            req.products.map {
                ProductOperationService.ProductUpdate(
                    productId = it.productId,
                    updatedPrice = it.updatedPrice,
                )
            },
        )
    }

    @DeleteMapping("/products")
    fun deleteProducts(
        @RequestBody req: ProductDeleteReq,
    ) {
        productOperationService.deleteProducts(
            req.targets.map {
                ProductOperationService.ProductDelete(it.productId)
            },
        )
    }
}
