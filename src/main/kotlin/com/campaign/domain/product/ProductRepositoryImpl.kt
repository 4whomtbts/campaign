package com.campaign.domain.product

import com.campaign.domain.Brand
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths.path
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.domain.Pageable

class ProductRepositoryImpl(
    private val executor: KotlinJdslJpqlExecutor,
) : ProductCustomRepository {
    override fun minPriceProductInAllCategories(): List<Product> =
        executor.findAll {
            val minPrices = select<MinPriceProductInCategoryProjection>(
                path(Product::category).alias(expression("category1")),
                min(Product::price).alias(expression("price1")),
            ).from(
                entity(Product::class),
            ).where(
                path(Product::deactivatedAt).isNull(),
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
            ).where(
                path(Product::deactivatedAt).isNull(),
            )
        } as List<Product>

    override fun selectMinPriceProductInAllCategoryByBrand(): List<MinPriceProductInAllCategoryByBrand> =
        executor.findAll {
            selectNew<MinPriceProductInAllCategoryByBrand>(
                path(Product::brand)(Brand::id),
                path(Product::brand)(Brand::brandName),
                path(Product::category) as Path<Int>,
                min(path(Product::price)),
            ).from(
                entity(Product::class),
                innerJoin(Product::brand).on(path(Brand::deactivatedAt).isNull()),
            ).where(
                path(Product::deactivatedAt).isNull(),
            ).groupBy(
                path(Product::brand),
                path(Product::category),
            )
        } as List<MinPriceProductInAllCategoryByBrand>

    override fun selectMinPriceProductInCategory(category: Product.ProductCategory): Product? =
        selectExtremumMinPriceProductInCategory(category, Sorts.asc(path(Product::price)))

    override fun selectMaxPriceProductInCategory(category: Product.ProductCategory): Product? =
        selectExtremumMinPriceProductInCategory(category, Sorts.desc(path(Product::price)))

    private fun selectExtremumMinPriceProductInCategory(category: Product.ProductCategory, sort: Sort): Product? =
        executor.findPage(Pageable.ofSize(1)) {
            val product = entity(Product::class)
            select(product)
                .from(
                    product,
                    innerJoin(Product::brand).on(path(Brand::deactivatedAt).isNull()),
                )
                .whereAnd(
                    path(Product::category).eq(category),
                    path(Product::deactivatedAt).isNull(),
                )
                .orderBy(sort)
        }.content.let {
            if (it.isEmpty()) {
                return null
            }
            it.first()
        }
}

private data class MinPriceProductInCategoryProjection(
    val category: Int,
    val price: Long,
)

data class MinPriceProductInAllCategoryByBrand(
    val brandId: Long,
    val brandName: String,
    val category: Product.ProductCategory,
    val price: Long,
)
