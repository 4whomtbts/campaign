package com.campaign.domain.product

interface ProductCustomRepository {
    fun minPriceProductInAllCategories(): List<Product>
}
