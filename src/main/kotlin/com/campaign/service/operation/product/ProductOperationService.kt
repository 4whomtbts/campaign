package com.campaign.service.operation.product

import com.campaign.domain.brand.BrandRepository
import com.campaign.domain.product.Product
import com.campaign.domain.product.ProductPrice
import com.campaign.domain.product.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProductOperationService(
    private val brandRepository: BrandRepository,
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun createProducts(productCreateList: List<ProductCreate>) {
        productCreateList.map {
            val brand = brandRepository.findByIdAndDeactivatedAtIsNull(it.brandId)
                ?: throw IllegalArgumentException("존재하지 않는 브랜드 입니다. brandId = ${it.brandId}")
            Product(
                brand = brand,
                price = it.price.value,
                category = Product.ProductCategory.ofCode(it.productCategoryCode),
            )
        }.let {
            productRepository.saveAll(it)
        }
    }

    @Transactional
    fun updateProducts(productUpdateList: List<ProductUpdate>) {
        val productMap = productUpdateList.groupBy(ProductUpdate::productId)
        val productEntities =
            productRepository.findAllByIdInAndDeactivatedAtIsNull(productUpdateList.map(ProductUpdate::productId))

        productEntities.forEach {
            val productUpdate =
                productMap[it.id] ?: throw IllegalArgumentException("존재하지 않는 상품 입니다. productId = ${it.id}")
            if (productUpdate.size > 1) {
                throw IllegalArgumentException("동일한 상품에 대해서 복수의 업데이트 요청을 할 수 없습니다. productId = ${it.id}")
            }
            val update = productUpdate.first()
            it.price = update.updatedPrice.value
        }
    }

    @Transactional
    fun deleteProducts(productDeleteList: List<ProductDelete>) {
        val productDeleteTargetIds = productDeleteList.map(ProductDelete::productId)
        val productEntities = productRepository.findAllByIdInAndDeactivatedAtIsNull(productDeleteTargetIds)

        productEntities.forEach {
            it.deactivatedAt = LocalDateTime.now()
        }
    }

    data class ProductCreate(
        val brandId: Long,
        val productCategoryCode: Int,
        val price: ProductPrice,
    )

    data class ProductUpdate(
        val productId: Long,
        val updatedPrice: ProductPrice,
    )

    data class ProductDelete(
        val productId: Long,
    )
}
