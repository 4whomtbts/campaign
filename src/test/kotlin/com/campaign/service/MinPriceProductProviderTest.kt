package com.campaign.service

import com.campaign.domain.product.Product
import com.campaign.service.MinPriceProductProvider.ProductDetail
import com.campaign.testsupports.IntegrationTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MinPriceProductProviderTest(
    @Autowired private val sut: MinPriceProductProvider,
) : IntegrationTestBase() {
    @Test
    fun `모든 카테고리 별 최저가격의 상품과 상품의 브랜드명을 포함한 테이블을 반환한다`() {
        val brandA = saveBrand(brandName = "A")
        val brandB = saveBrand(brandName = "B")
        val brandC = saveBrand(brandName = "C")

        val minPriceInTop = saveProduct(Product.ProductCategory.TOP, 100L, brandA)
        saveProduct(Product.ProductCategory.TOP, 10_000L, brandB)
        saveProduct(Product.ProductCategory.TOP, 50_000L, brandC)

        val minPriceInOutWear = saveProduct(Product.ProductCategory.OUTWEAR, 500L, brandA)
        saveProduct(Product.ProductCategory.OUTWEAR, 35_000L, brandB)
        saveProduct(Product.ProductCategory.OUTWEAR, 40_000L, brandC)

        val minPriceInAcc = saveProduct(Product.ProductCategory.ACCESSORIES, 1000L, brandA)
        saveProduct(Product.ProductCategory.ACCESSORIES, 150_000L, brandB)
        saveProduct(Product.ProductCategory.ACCESSORIES, 200_000L, brandC)

        val result = sut.productTableForAllCategories()
        val products = result.products

        assertEquals(3, products.size)
        assertTrue(
            products.containsAll(
                listOf(
                    ProductDetail.of(minPriceInTop),
                    ProductDetail.of(minPriceInOutWear),
                    ProductDetail.of(minPriceInAcc),
                ),
            ),
        )
    }

    @Test
    fun `최저가인 상품이 복수개 존재하는 상품은 브랜드 이름이 사전순으로 뒤에 있는 상품이 선택된다`() {
        val brandA = saveBrand(brandName = "A")
        val brandABC = saveBrand(brandName = "ABC")
        val brandZ = saveBrand(brandName = "Z")
        val commonPrice = 100L

        saveProduct(Product.ProductCategory.TOP, commonPrice, brandA)
        saveProduct(Product.ProductCategory.TOP, commonPrice, brandABC)
        val expectSelected = saveProduct(Product.ProductCategory.TOP, commonPrice, brandZ)

        val result = sut.productTableForAllCategories()
        val products = result.products

        assertEquals(1, products.size)
        assertTrue(
            products.containsAll(
                listOf(
                    ProductDetail.of(expectSelected),
                ),
            ),
        )
    }
}
