package com.campaign.service

import com.campaign.domain.product.Product
import com.campaign.service.model.ProductDetail
import com.campaign.testsupports.IntegrationTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductPriceProviderTest(
    @Autowired private val sut: ProductPriceProvider,
) : IntegrationTestBase() {
    @Nested
    inner class QueryMinPriceToPurchaseAllCategoryContext {
        @Test
        fun `모든 카테고리 별 최저가격의 상품과 상품의 브랜드명을 포함한 정보를 반환한다`() {
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

            val result = sut.queryMinPriceToPurchaseAllCategory()
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

            val result = sut.queryMinPriceToPurchaseAllCategory()
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

    @Nested
    inner class QueryMinPurchasePriceForAllCategoryInSingleBrandContext {
        @Test
        fun `상품이 존재하지 않으면 null 을 반환한다`() {
            assertNull(sut.queryMinPurchasePriceForAllCategoryInSingleBrand())
        }

        @Test
        fun `모든 카테고리의 상품을 가장 저렴하게 살 수 있는 브랜드와 해당 브랜드의 상품 목록을 반환한다`() {
            val brandA = saveBrand(brandName = "A")
            val brandB = saveBrand(brandName = "B")

            val expect = listOf(
                saveProduct(Product.ProductCategory.TOP, 10_000L, brandA),
                saveProduct(Product.ProductCategory.OUTWEAR, 34_999L, brandA),
            )

            saveProduct(Product.ProductCategory.TOP, 10_000L, brandB)
            saveProduct(Product.ProductCategory.OUTWEAR, 35_000L, brandB)

            val result = sut.queryMinPurchasePriceForAllCategoryInSingleBrand()!!
            assertTrue(
                result.products.containsAll(
                    expect.map {
                        ProductDetail(
                            category = it.category,
                            brandName = it.brand.brandName,
                            price = it.price,
                        )
                    },
                ),
            )
            assertEquals(brandA.brandName, result.brandName)
        }
    }

    @Nested
    inner class QueryMinAndMaxPriceInCategoryContext {
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
}
