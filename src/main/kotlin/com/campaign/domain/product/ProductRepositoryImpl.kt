package com.campaign.domain.product

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import jakarta.persistence.EntityManager

class ProductRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
    private val entityManager: EntityManager,
) : ProductCustomRepository {
    override fun minPriceProductInAllCategories(): List<Product> =
        executor.findAll {
            val minPrices = select<MinPriceProductInCategoryProjection>(
                path(Product::category).alias(expression("category1")),
                min(Product::price).alias(expression("price1")),
            ).from(
                entity(Product::class),
            ).groupBy(
                path(Product::category),
            ).asEntity("minPrices")

            select(
                entity(Product::class),
            ).from(
                entity(Product::class),
                innerFetchJoin(Product::brand),
                join(minPrices).on(
                    and(
                        path(Product::category).eq(expression("category1")),
                        path(Product::price).eq(expression("price1")),
                    ),
                ),
            )
        } as List<Product>
}

private data class MinPriceProductInCategoryProjection(
    val category: Int,
    val price: Long,
)
