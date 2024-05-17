package com.campaign.testsupports

import com.campaign.domain.Brand
import com.campaign.domain.brand.BrandRepository
import com.campaign.domain.product.Product
import com.campaign.domain.product.ProductRepository
import com.campaign.service.MinPriceProductProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.transaction.annotation.Transactional

@Profile("test")
@SpringBootTest
@Transactional
class IntegrationTestBase {
    @Autowired
    private lateinit var productService: MinPriceProductProvider

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    fun saveBrand(brandName: String) =
        brandRepository.save(Brand(brandName = brandName))

    fun saveProduct(category: Product.ProductCategory, price: Long, brand: Brand) =
        productRepository.save(Product(category = category, price = price, brand = brand))
}
