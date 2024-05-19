package com.campaign.domain.product

import com.campaign.testsupports.IntegrationTestBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ProductRepositoryImplTest(
    @Autowired private val sut: ProductRepository,
) : IntegrationTestBase() {
    @Nested
    inner class MinPriceProductInAllCategoriesContext {
        @Test
        fun `특정 카테고리에서 가장 저렴한 상품을 반환한다`() {
            val brandA = saveBrand(brandName = "A")
            val brandB = saveBrand(brandName = "B")
            val brandC = saveBrand(brandName = "C")

            saveProduct(Product.ProductCategory.TOP, 1_000L, brandA)
            saveProduct(Product.ProductCategory.BAG, 900L, brandA)
            saveProduct(Product.ProductCategory.ACCESSORIES, 900L, brandB)
            saveProduct(Product.ProductCategory.ACCESSORIES, 1_000L, brandC)

            val products = sut.minPriceProductInAllCategories()
            assertEquals(3, products.size)
        }

        @Test
        fun `동일한 카테고리에서 최저가인 상품이 복수개 존재한다면 복수개 모두 반환한다`() {
            val brandA = saveBrand(brandName = "A")
            val brandB = saveBrand(brandName = "B")

            saveProduct(Product.ProductCategory.TOP, 1_000L, brandA)
            saveProduct(Product.ProductCategory.BAG, 1_000L, brandB)

            val products = sut.minPriceProductInAllCategories()
            assertEquals(2, products.size)
        }
    }

    @Nested
    inner class C {
        @Test
        fun `각 브랜드가 모든 카테고리 각각에서 최저가로 판매하는 상품을 반환한다`() {
            val brandA = saveBrand(brandName = "A")
            val brandB = saveBrand(brandName = "B")

            saveProduct(Product.ProductCategory.TOP, 2_000L, brandA)
            saveProduct(Product.ProductCategory.BAG, 5_000L, brandA)
            saveProduct(Product.ProductCategory.TOP, 2_000L, brandB)
            saveProduct(Product.ProductCategory.BAG, 5_000L, brandB)

            val minPriceProductInBrandA = listOf(
                saveProduct(Product.ProductCategory.TOP, 1L, brandA),
                saveProduct(Product.ProductCategory.TOP, 1L, brandA),
                saveProduct(Product.ProductCategory.BAG, 1L, brandA),
            )
            val minPriceProductInBrandB = listOf(
                saveProduct(Product.ProductCategory.TOP, 1L, brandB),
                saveProduct(Product.ProductCategory.TOP, 1L, brandB),
                saveProduct(Product.ProductCategory.BAG, 1L, brandB),
            )

            val products = sut.selectMinPriceProductInAllCategoriesByBrand()
            assertEquals(4, products.size)
            assertTrue(
                products.containsAll(
                    (minPriceProductInBrandA + minPriceProductInBrandB).map {
                        MinPriceProductInAllCategoryByBrand(
                            brandId = it.brand.id,
                            brandName = it.brand.brandName,
//                            category = it.category.code,
                            category = it.category,
                            price = it.price,
                        )
                    },
                ),
            )
        }
    }

    @Nested
    inner class SelectMinPriceProductInCategoryContext {
        @Test
        fun `주어진 카테고리에 대하여 상품이 존재하지 않으면 null 을 반환한다`() {
            assertNull(sut.selectMinPriceProductInCategory(Product.ProductCategory.TOP))
        }

        @Test
        fun `주어진 카테고리에 대하여 최저가 상품을 조회한다`() {
            val brandA = saveBrand(brandName = "A")
            val brandB = saveBrand(brandName = "B")

            val expect = saveProduct(Product.ProductCategory.TOP, 1L, brandA)
            saveProduct(Product.ProductCategory.TOP, 5_000L, brandB)

            val result = sut.selectMinPriceProductInCategory(Product.ProductCategory.TOP)
            assertEquals(expect, result)
        }
    }

    @Nested
    inner class SelectMaxPriceProductInCategoryContext {
        @Test
        fun `주어진 카테고리에 대하여 상품이 존재하지 않으면 null 을 반환한다`() {
            assertNull(sut.selectMinPriceProductInCategory(Product.ProductCategory.TOP))
        }

        @Test
        fun `주어진 카테고리에 대하여 최고가 상품을 조회한다`() {
            val brandA = saveBrand(brandName = "A")
            val brandB = saveBrand(brandName = "B")

            saveProduct(Product.ProductCategory.TOP, 1L, brandA)
            val expect = saveProduct(Product.ProductCategory.TOP, 5_000L, brandB)

            val result = sut.selectMaxPriceProductInCategory(Product.ProductCategory.TOP)
            assertEquals(expect, result)
        }
    }
}
