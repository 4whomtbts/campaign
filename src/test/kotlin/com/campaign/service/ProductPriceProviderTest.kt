package com.campaign.service

import com.campaign.domain.product.Product
import com.campaign.testsupports.IntegrationTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductPriceProviderTest(
    @Autowired private val sut: ProductPriceProvider,
) : IntegrationTestBase() {
    @Test
    fun `주어진 카테고리에 상품이 존재하지 않는다면 최소, 최대 가격 상품은 모두 null 이다`() {
        val category = Product.ProductCategory.TOP
        val result = sut.queryMinAndMaxPriceInCategory(category)
        assertEquals(category, result.category)
        assertNull(result.minProduct)
        assertNull(result.maxProduct)
    }

    @Test
    fun `주어진 카테고리의 최소, 최대 가격의 상품정보를 담은 객체를 반환한다`() {
        val brandA = saveBrand(brandName = "A")
        val brandB = saveBrand(brandName = "B")
        val category = Product.ProductCategory.TOP

        sut.queryMinAndMaxPriceInCategory(category)
        val minProduct = saveProduct(category, 1L, brandA)
        saveProduct(category, 100L, brandA)
        saveProduct(category, 9_999L, brandB)
        val maxProduct = saveProduct(category, 10_000L, brandB)

        val result = sut.queryMinAndMaxPriceInCategory(category)
        assertEquals(result.category, category)
        assertEquals(minProduct, result.minProduct!!)
        assertEquals(maxProduct, result.maxProduct!!)
    }
}
