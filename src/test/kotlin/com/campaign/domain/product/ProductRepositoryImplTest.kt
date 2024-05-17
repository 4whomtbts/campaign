package com.campaign.domain.product

import com.campaign.domain.brand.BrandRepository
import com.campaign.testsupports.IntegrationTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductRepositoryImplTest(
    @Autowired private val brandRepository: BrandRepository,
    @Autowired private val productRepository: ProductRepository,
) : IntegrationTestBase() {
    @Test
    fun `특정 카테고리에서 가장 저렴한 상품을 반환한다`() {
        val brandA = saveBrand(brandName = "A")
        val brandB = saveBrand(brandName = "B")
        val brandC = saveBrand(brandName = "C")

        saveProduct(Product.ProductCategory.TOP, 1_000L, brandA)
        saveProduct(Product.ProductCategory.BAG, 900L, brandA)
        saveProduct(Product.ProductCategory.ACCESSORIES, 900L, brandB)
        saveProduct(Product.ProductCategory.ACCESSORIES, 1_000L, brandC)

        val products = productRepository.minPriceProductInAllCategories()
        assertEquals(3, products.size)
    }

    @Test
    fun `동일한 카테고리에서 최저가인 상품이 복수개 존재한다면 복수개 모두 반환한다`() {
        val brandA = saveBrand(brandName = "A")
        val brandB = saveBrand(brandName = "B")

        saveProduct(Product.ProductCategory.TOP, 1_000L, brandA)
        saveProduct(Product.ProductCategory.BAG, 1_000L, brandB)

        val products = productRepository.minPriceProductInAllCategories()
        assertEquals(2, products.size)
    }
}
