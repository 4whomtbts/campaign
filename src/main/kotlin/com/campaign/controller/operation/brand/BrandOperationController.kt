package com.campaign.controller.operation.brand

import com.campaign.controller.DefaultResponse
import com.campaign.controller.operation.brand.model.request.BrandCreateReq
import com.campaign.controller.operation.brand.model.request.BrandDeleteReq
import com.campaign.controller.operation.brand.model.request.BrandDetail
import com.campaign.controller.operation.brand.model.request.BrandUpdateReq
import com.campaign.service.operation.brand.BrandOperationService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/operation/v1")
class BrandOperationController(
    private val brandOperationService: BrandOperationService,
) {
    @PostMapping("/brands")
    fun createBrands(
        @RequestBody req: BrandCreateReq,
    ): DefaultResponse {
        return brandOperationService.createBrands(req.brands.map(BrandDetail::brandName))
            .let {
                DefaultResponse.ok()
            }
    }

    @PutMapping("/brands")
    fun updateBrands(
        @RequestBody req: BrandUpdateReq,
    ): DefaultResponse {
        return brandOperationService.updateBrands(
            req.brands.map {
                BrandOperationService.BrandUpdate(
                    brandId = it.brandId,
                    brandName = it.updatedBrandName,
                )
            },
        ).let {
            DefaultResponse.ok()
        }
    }

    @DeleteMapping("/brands")
    fun deleteBrands(
        @RequestBody req: BrandDeleteReq,
    ): DefaultResponse {
        return brandOperationService.deleteBrands(
            req.targets.map {
                BrandOperationService.BrandDelete(it.brandId)
            },
        )
            .let {
                DefaultResponse.ok()
            }
    }
}
