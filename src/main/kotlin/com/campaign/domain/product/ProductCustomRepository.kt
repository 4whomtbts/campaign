package com.campaign.domain.product

interface ProductCustomRepository {
    fun minPriceProductInAllCategories(): List<Product>
    fun selectMinPriceProductInAllCategoryByBrand(): List<MinPriceProductInAllCategoryByBrand>
    fun selectMinPriceProductInCategory(category: Product.ProductCategory): Product?

    fun selectMaxPriceProductInCategory(category: Product.ProductCategory): Product?
}
